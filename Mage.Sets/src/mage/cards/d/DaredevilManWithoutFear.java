package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksWithCreaturesTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.common.continuous.LookAtTopCardOfLibraryAnyTimeEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.players.Player;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author muz
 */
public final class DaredevilManWithoutFear extends CardImpl {

    public DaredevilManWithoutFear(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{W}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.HERO);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Radar Sense -- You may look at the top card of your library any time.
        this.addAbility(new SimpleStaticAbility(new LookAtTopCardOfLibraryAnyTimeEffect()).withFlavorWord("Radar Sense"));

        // Whenever you attack, you may exile the top card of your library. If that card is a Hero card, Daredevil gets +2/+1 until end of turn. You may play that card this turn.
        this.addAbility(new AttacksWithCreaturesTriggeredAbility(
            Zone.BATTLEFIELD, new DaredevilManWithoutFearEffect(),
            1, StaticFilters.FILTER_PERMANENT_CREATURES, false, true
        ));
    }

    private DaredevilManWithoutFear(final DaredevilManWithoutFear card) {
        super(card);
    }

    @Override
    public DaredevilManWithoutFear copy() {
        return new DaredevilManWithoutFear(this);
    }
}

class DaredevilManWithoutFearEffect extends OneShotEffect {

    DaredevilManWithoutFearEffect() {
        super(Outcome.Benefit);
        staticText = "exile the top card of your library. If that card is a Hero card, Daredevil gets +2/+1 until end of turn. You may play that card this turn";
    }

    private DaredevilManWithoutFearEffect(final DaredevilManWithoutFearEffect effect) {
        super(effect);
    }

    @Override
    public DaredevilManWithoutFearEffect copy() {
        return new DaredevilManWithoutFearEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        Card card = controller.getLibrary().getFromTop(game);
        if (card == null) {
            return true;
        }
        controller.moveCardsToExile(
            card, source, game, true,
            CardUtil.getExileZoneId(game, source),
            CardUtil.getSourceName(game, source)
        );
        if (!Zone.EXILED.equals(game.getState().getZone(card.getId()))) {
            return true;
        }
        if (card.hasSubtype(SubType.HERO, game)) {
            game.addEffect(new BoostSourceEffect(2, 1, Duration.EndOfTurn), source);
        }
        game.addEffect(new PlayFromNotOwnHandZoneTargetEffect(Zone.EXILED, Duration.EndOfTurn)
            .setTargetPointer(new FixedTarget(card, game)), source);
        return true;
    }
}
