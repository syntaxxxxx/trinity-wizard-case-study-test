# Trinity Wizard Case Study Test

## Story: User requests to see avengers

## Flowchart

![Avenger Flowchart](avenger_flowchart.png)

## Avenger Architecture

![Avenger Architecture](avenger_architecture.png)

## BDD Specs

## Avenger Feature

### Narrative #1

```
As an online or offline user
I want the app to automatically load avenger
So I can see the newest avenger
```

#### Scenarios (Acceptance criteria)

```
Given the user has connectivity or not
When the user requests to see avenger
Then the app should display the latest avenger from local
```

## Use Cases

### Load Avenger From Local Use Case

#### Primary Course (Happy Path):
1. Execute "Load Avenger" command.
2. System creates avenger from valid data.
3. System delivers avenger.

#### Unexpected – Error Course (Sad Path):
1. System delivers unexpected error.

## Model Specs

### Avenger

| Property | Type             |
|----------|------------------|
| `id`     | `String`         |
| `name`   | `String`         |
| `rating` | `String`         |
| `image`  | `Int` (optional) |