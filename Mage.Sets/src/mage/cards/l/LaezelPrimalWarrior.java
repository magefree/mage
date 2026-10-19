package mage.cards.l;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.DoubleStrikeAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.PerpetualUtil;

import java.util.UUID;

public final class LaezelPrimalWarrior extends CardImpl {

    public LaezelPrimalWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{G}{W}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GITH);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(6);

        this.extraDeckCard = true;
        this.addAbility(DoubleStrikeAbility.getInstance());
        this.addAbility(new LaezelSpecializedTriggeredAbility(new LaezelPrimalWarriorEffect()));
    }

    private LaezelPrimalWarrior(final LaezelPrimalWarrior card) {
        super(card);
    }

    @Override
    public LaezelPrimalWarrior copy() {
        return new LaezelPrimalWarrior(this);
    }
}

class LaezelPrimalWarriorEffect extends OneShotEffect {

    LaezelPrimalWarriorEffect() {
        super(Outcome.Benefit);
        staticText = "other creatures you control and creature cards in your hand perpetually get +1/+1";
    }

    private LaezelPrimalWarriorEffect(final LaezelPrimalWarriorEffect effect) {
        super(effect);
    }

    @Override
    public LaezelPrimalWarriorEffect copy() {
        return new LaezelPrimalWarriorEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        for (Permanent permanent : game.getBattlefield().getAllActivePermanents(player.getId())) {
            if (permanent.isCreature(game) && !permanent.getId().equals(source.getSourceId())) {
                Card card = game.getCard(permanent.getId());
                if (card != null) {
                    PerpetualUtil.boost(card, 1, 1, source, game);
                } else {
                    game.addEffect(new BoostTargetEffect(1, 1, Duration.Custom)
                            .setTargetPointer(new FixedTarget(permanent, game)), source);
                }
            }
        }
        for (Card card : player.getHand().getCards(StaticFilters.FILTER_CARD_CREATURE, game)) {
            PerpetualUtil.boost(card, 1, 1, source, game);
        }
        return true;
    }
}
