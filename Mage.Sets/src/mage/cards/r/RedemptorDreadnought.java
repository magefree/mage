package mage.cards.r;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
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
        this.addAbility(new AttacksTriggeredAbility(new RedemptorDreadnoughtEffect()).withFlavorWord("Plasma Incinerator"));

    }

    private RedemptorDreadnought(final RedemptorDreadnought card) {
        super(card);
    }

    @Override
    public RedemptorDreadnought copy() {
        return new RedemptorDreadnought(this);
    }
}

class RedemptorDreadnoughtEffect extends OneShotEffect {

    RedemptorDreadnoughtEffect() {
        super(Outcome.BoostCreature);
        staticText = "if a card is exiled with it, it gets +X/+X until end of turn, where X is the power of the exiled card";
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
