package mage.cards.f;

import mage.ConditionalMana;
import mage.MageObject;
import mage.Mana;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.Cost;
import mage.abilities.costs.CostImpl;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ReturnFromGraveyardToBattlefieldTargetEffect;
import mage.abilities.hint.HintUtils;
import mage.abilities.mana.ConditionalColoredManaAbility;
import mage.abilities.mana.builder.ConditionalManaBuilder;
import mage.cards.*;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.filter.FilterPermanent;
import mage.filter.StaticFilters;
import mage.filter.common.FilterControlledArtifactPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.Target;
import mage.target.TargetPermanent;
import mage.target.common.TargetCardInYourGraveyard;

import java.awt.*;
import java.util.Objects;
import java.util.UUID;

/**
 * @author notgreat
 */
public final class FabricationFoundry extends CardImpl {

    public FabricationFoundry(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "{1}{W}");


        // {T}: Add {W}. Spend this mana only to cast an artifact spell or activate an ability of an artifact source.
        this.addAbility(new ConditionalColoredManaAbility(Mana.WhiteMana(1), new ArtifactManaBuilder()));

        // {2}{W}, {T}, Exile one or more other artifacts you control with total mana value X: Return target artifact card with mana value X or less from your graveyard to the battlefield. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(new ReturnFromGraveyardToBattlefieldTargetEffect(), new ManaCostsImpl<>("{2}{W}"));
        ability.addCost(new TapSourceCost());
        ability.addCost(new ExileTargetsTotalManaValueCost());
        Target target = new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD);
        target.withTargetName("artifact card with mana value X or less from your graveyard");
        ability.addTarget(target);
        this.addAbility(ability);
    }

    private FabricationFoundry(final FabricationFoundry card) {
        super(card);
    }

    @Override
    public FabricationFoundry copy() {
        return new FabricationFoundry(this);
    }
}

//Mana based on Oaken Siren
class ArtifactManaBuilder extends ConditionalManaBuilder {

    @Override
    public ConditionalMana build(Object... options) {
        return new ArtifactConditionalMana(this.mana);
    }

    @Override
    public String getRule() {
        return "Spend this mana only to cast an artifact spell or activate an ability of an artifact source";
    }
}

class ArtifactConditionalMana extends ConditionalMana {

    ArtifactConditionalMana(Mana mana) {
        super(mana);
        addCondition(ArtifactSpellOrActivatedAbilityCondition.instance);
    }
}

enum ArtifactSpellOrActivatedAbilityCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject object = game.getObject(source);
        return object != null && object.isArtifact(game) && !source.isActivated();
    }
}

//Cost based on Kozilek, The Great Distortion and CrewAbility
class ExileTargetsTotalManaValueCost extends CostImpl {
    private static final FilterPermanent filter = new FilterControlledArtifactPermanent("one or more other artifacts you control with total mana value X");

    static {
        filter.add(AnotherPredicate.instance);
    }

    public ExileTargetsTotalManaValueCost() {
        this.text = "Exile one or more other artifacts you control with total mana value X";
    }

    public ExileTargetsTotalManaValueCost(ExileTargetsTotalManaValueCost cost) {
        super(cost);
    }

    @Override
    public boolean pay(Ability ability, Game game, Ability source, UUID controllerId, boolean noMana, Cost costToPay) {
        Card abilityTarget = game.getCard(ability.getFirstTarget());
        if (abilityTarget == null) {
            return paid;
        }
        Player player = game.getPlayer(ability.getControllerId());
        if (player == null) {
            return paid;
        }
        int minX = abilityTarget.getManaValue();
        int sum = 0;
        Target target = new TargetPermanent(1, Integer.MAX_VALUE, filter, true) {
            @Override
            public String getMessage(Game game) {
                // shows selected mana value
                int selectedPower = this.targets.keySet().stream()
                        .map(game::getPermanent)
                        .filter(Objects::nonNull)
                        .mapToInt(Permanent::getManaValue)
                        .sum();
                String extraInfo = "(selected mana value " + selectedPower + " of " + minX + ")";
                if (selectedPower >= minX) {
                    extraInfo = HintUtils.prepareText(extraInfo, Color.GREEN);
                }
                return super.getMessage(game) + " " + extraInfo;
            }
        };
        if (!target.choose(Outcome.Exile, controllerId, source.getSourceId(), source, game)) {
            return paid;
        }
        Cards cards = new CardsImpl();
        cards.addAll(target.getTargets());
        for (UUID targetId : target.getTargets()) {
            Permanent permanent = game.getPermanent(targetId);
            if (permanent != null) {
                sum += permanent.getManaValue();
            }
        }
        paid = (sum >= minX);
        if (paid) {
            player.moveCardsToExile(cards.getCards(game), source, game, false, null, null);
        }
        return paid;
    }

    @Override
    public boolean canPay(Ability ability, Ability source, UUID controllerId, Game game) {
        int totalExileMV = 0;
        boolean anyExileFound = false;
        for (Permanent permanent : game.getBattlefield().getActivePermanents(filter, controllerId, source, game)) {
            totalExileMV += permanent.getManaValue();
            anyExileFound = true;
        }
        int minTargetMV = Integer.MAX_VALUE;
        for (Card card : game.getPlayer(controllerId).getGraveyard().getCards(StaticFilters.FILTER_CARD_ARTIFACT_FROM_YOUR_GRAVEYARD, game)) {
            minTargetMV = Integer.min(minTargetMV, card.getManaValue());
        }
        return anyExileFound && totalExileMV >= minTargetMV;
    }

    @Override
    public ExileTargetsTotalManaValueCost copy() {
        return new ExileTargetsTotalManaValueCost(this);
    }
}
