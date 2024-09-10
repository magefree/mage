package mage.cards.n;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DiesCreatureTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CreateTokenCopyTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledCreaturePermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.filter.predicate.permanent.TokenPredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class NightmareShepherd extends CardImpl {

    private static final FilterPermanent filter
            = new FilterControlledCreaturePermanent("another nontoken creature you control");

    static {
        filter.add(TokenPredicate.FALSE);
        filter.add(AnotherPredicate.instance);
    }

    public NightmareShepherd(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{2}{B}{B}");

        this.subtype.add(SubType.DEMON);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever another nontoken creature you control dies, you may exile it. If you do, create a token that's a copy of that creature, except it's 1/1 and it's a Nightmare in addition to its other types.
        this.addAbility(new DiesCreatureTriggeredAbility(
                new NightmareShepherdEffect(), true, filter, true
        ));
    }

    private NightmareShepherd(final NightmareShepherd card) {
        super(card);
    }

    @Override
    public NightmareShepherd copy() {
        return new NightmareShepherd(this);
    }
}

class NightmareShepherdEffect extends OneShotEffect {

    NightmareShepherdEffect() {
        super(Outcome.Benefit);
        staticText = "exile it. If you do, create a token that's a copy of that creature, " +
                "except it's 1/1 and it's a Nightmare in addition to its other types.";
    }

    private NightmareShepherdEffect(final NightmareShepherdEffect effect) {
        super(effect);
    }

    @Override
    public NightmareShepherdEffect copy() {
        return new NightmareShepherdEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        Card card = game.getCard(getTargetPointer().getFirst(game, source));
        if (player == null || card == null) {
            return false;
        }
        CreateTokenCopyTargetEffect effect = new CreateTokenCopyTargetEffect(
                source.getControllerId(), null, false, 1, false,
                false, null, 1, 1, false
        );
        effect.setTargetPointer(new FixedTarget(card.getId(), card.getZoneChangeCounter(game) + 1));
        effect.withAdditionalSubType(SubType.NIGHTMARE);
        player.moveCards(card, Zone.EXILED, source, game);
        effect.apply(game, source);
        return true;
    }
}
