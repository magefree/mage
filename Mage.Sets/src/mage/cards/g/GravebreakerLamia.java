package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.SearchEffect;
import mage.abilities.effects.common.cost.SpellsCostReductionControllerEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.other.CastFromZonePredicate;
import mage.game.Game;
import mage.players.Player;
import mage.target.common.TargetCardInLibrary;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class GravebreakerLamia extends CardImpl {

    private static final FilterCard filter = new FilterCard();

    static {
        filter.add(new CastFromZonePredicate(Zone.GRAVEYARD));
    }

    public GravebreakerLamia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "{4}{B}");

        this.subtype.add(SubType.SNAKE);
        this.subtype.add(SubType.LAMIA);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Lifelink
        this.addAbility(LifelinkAbility.getInstance());

        // When Gravebreaker Lamia enters the battlefield, search your library for a card, put it into your graveyard, then shuffle your library.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new GravebreakerLamiaSearchEffect(), false));

        // Spells you cast from your graveyard cost {1} less to cast.
        this.addAbility(new SimpleStaticAbility(new SpellsCostReductionControllerEffect(filter, 1)
                .setText("Spells you cast from your graveyard cost {1} less to cast.")));
    }

    private GravebreakerLamia(final GravebreakerLamia card) {
        super(card);
    }

    @Override
    public GravebreakerLamia copy() {
        return new GravebreakerLamia(this);
    }
}

class GravebreakerLamiaSearchEffect extends SearchEffect {

    GravebreakerLamiaSearchEffect() {
        super(new TargetCardInLibrary(), Outcome.Neutral);
        staticText = "search your library for a card, put it into your graveyard, then shuffle your library";
    }

    private GravebreakerLamiaSearchEffect(final GravebreakerLamiaSearchEffect effect) {
        super(effect);
    }

    @Override
    public GravebreakerLamiaSearchEffect copy() {
        return new GravebreakerLamiaSearchEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        if (controller.searchLibrary(target, source, game)) {
            controller.moveCards(game.getCard(target.getFirstTarget()), Zone.GRAVEYARD, source, game);
        }
        controller.shuffleLibrary(source, game);
        return true;
    }
}
