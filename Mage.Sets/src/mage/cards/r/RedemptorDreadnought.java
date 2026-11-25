package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.costs.common.ExileFromGraveCost;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.filter.StaticFilters;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.common.TargetCardInYourGraveyard;
import mage.util.CardUtil;

import java.util.Objects;
import java.util.UUID;

/**
 * @author xenohedron
 */
public final class RedemptorDreadnought extends CardImpl {

    public RedemptorDreadnought(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{5}");
        
        this.subtype.add(SubType.ASTARTES);
        this.subtype.add(SubType.DREADNOUGHT);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Fallen Warrior -- As an additional cost to cast this spell, you may exile a creature card from your graveyard.
        this.getSpellAbility().addCost(new ExileFromGraveCost(
                new TargetCardInYourGraveyard(0, 1, StaticFilters.FILTER_CARD_CREATURE_YOUR_GRAVEYARD)
        ).setText(""));

        this.addAbility(new SimpleStaticAbility(new InfoEffect("as an additional cost to cast this spell, you may exile a creature card from your graveyard"))
                .setRuleAtTheTop(true).withFlavorWord("Fallen Warrior"));

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // Plasma Incinerator -- Whenever Redemptor Dreadnought attacks, if a card is exiled with it, it gets +X/+X until end of turn, where X is the power of the exiled card.
        this.addAbility(new AttacksTriggeredAbility(new RedemptorDreadnoughtEffect())
                .withInterveningIf(RedemptorDreadnoughtCondition.instance).withFlavorWord("Plasma Incinerator"));

    }

    private RedemptorDreadnought(final RedemptorDreadnought card) {
        super(card);
    }

    @Override
    public RedemptorDreadnought copy() {
        return new RedemptorDreadnought(this);
    }
}

/**
 * "if a card is exiled with it" is an intervening-if (CR 603.4): with nothing exiled with the
 * permanent the ability must not trigger at all. Reads the same exile zone the cost wrote to and the
 * effect reads at resolution (the permanent's zcc is one above the spell's).
 */
enum RedemptorDreadnoughtCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        // the trigger-check ability has no stack-moment zcc yet (0): read the permanent's own
        int zcc = source.getStackMomentSourceZCC();
        if (zcc == 0) {
            Permanent permanent = source.getSourcePermanentOrLKI(game);
            if (permanent == null) {
                return false;
            }
            zcc = permanent.getZoneChangeCounter(game);
        }
        ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source.getSourceId(), zcc - 1));
        return exile != null && !exile.isEmpty();
    }

    @Override
    public String toString() {
        return "a card is exiled with it";
    }
}

class RedemptorDreadnoughtEffect extends OneShotEffect {

    RedemptorDreadnoughtEffect() {
        super(Outcome.BoostCreature);
        staticText = "it gets +X/+X until end of turn, where X is the power of the exiled card";
    }

    private RedemptorDreadnoughtEffect(final RedemptorDreadnoughtEffect effect) {
        super(effect);
    }

    @Override
    public RedemptorDreadnoughtEffect copy() {
        return new RedemptorDreadnoughtEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, source, -1));
        if (exile == null || exile.isEmpty()) {
            return false;
        }
        int xValue = exile.getCards(game)
                .stream()
                .filter(Objects::nonNull)
                .map(MageObject::getPower)
                .mapToInt(MageInt::getValue)
                .sum();
        if (xValue > 0) {
            game.addEffect(new BoostSourceEffect(xValue, xValue, Duration.EndOfTurn), source);
        }
        return true;
    }


}
