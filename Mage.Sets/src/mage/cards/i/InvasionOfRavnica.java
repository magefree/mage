package mage.cards.i;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.FilterPermanent;
import mage.filter.FilterSpell;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.Predicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfRavnica extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter = new FilterNonlandPermanent("nonland permanent an opponent controls that isn't exactly two colors");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(InvasionOfRavnicaPredicate.instance);
    }

    private static final FilterSpell spellFilter = new FilterSpell("a spell that's exactly two colors");
    private static final FilterCard cardFilter = new FilterCard("a card that's exactly two colors");

    static {
        spellFilter.add(GuildpactParagonPredicate.instance);
        cardFilter.add(GuildpactParagonPredicate.instance);
    }

    public InvasionOfRavnica(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{5}",
                "Guildpact Paragon",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.CONSTRUCT}, ""
        );

        // Invasion of Ravnica
        this.getLeftHalfCard().setStartingDefense(4);
        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Ravnica enters the battlefield, exile target nonland permanent an opponent controls that isn't exactly two colors.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.getLeftHalfCard().addAbility(ability);

        // Guildpact Paragon
        this.getRightHalfCard().setPT(5, 5);

        // Whenever you cast a spell that's exactly two colors, look at the top six cards of your library. You may reveal a card that's exactly two colors from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(new LookLibraryAndPickControllerEffect(
                6, 1, cardFilter, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), spellFilter, false));
    }

    private InvasionOfRavnica(final InvasionOfRavnica card) {
        super(card);
    }

    @Override
    public InvasionOfRavnica copy() {
        return new InvasionOfRavnica(this);
    }
}

enum InvasionOfRavnicaPredicate implements Predicate<Permanent> {
    instance;

    @Override
    public boolean apply(Permanent input, Game game) {
        return input.getColor(game).getColorCount() != 2;
    }
}

enum GuildpactParagonPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        return input.getColor(game).getColorCount() == 2;
    }
}
