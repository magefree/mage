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
import mage.target.TargetPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfRavnica extends TransformingDoubleFacedCard {

    private static final FilterPermanent filter
            = new FilterNonlandPermanent("nonland permanent an opponent controls that isn't exactly two colors");
    private static final FilterSpell filter2 = new FilterSpell("a spell that's exactly two colors");
    private static final FilterCard filter3 = new FilterCard("a card that's exactly two colors");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
        filter.add(InvasionOfRavnicaPredicate.FALSE);
        filter2.add(InvasionOfRavnicaPredicate.TRUE);
        filter3.add(InvasionOfRavnicaPredicate.TRUE);
    }

    public InvasionOfRavnica(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{5}",
                "Guildpact Paragon",
                new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, new SubType[]{SubType.CONSTRUCT}, ""
        );
        this.getLeftHalfCard().setStartingDefense(4);
        this.getRightHalfCard().setPT(5, 5);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Ravnica enters the battlefield, exile target nonland permanent an opponent controls that isn't exactly two colors.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetPermanent(filter));
        this.getLeftHalfCard().addAbility(ability);

        // Guildpact Paragon
        // Whenever you cast a spell that's exactly two colors, look at the top six cards of your library. You may reveal a card that's exactly two colors from among them and put it into your hand. Put the rest on the bottom of your library in a random order.
        this.getRightHalfCard().addAbility(new SpellCastControllerTriggeredAbility(new LookLibraryAndPickControllerEffect(
                6, 1, filter3, PutCards.HAND, PutCards.BOTTOM_RANDOM
        ), filter2, false));
    }

    private InvasionOfRavnica(final InvasionOfRavnica card) {
        super(card);
    }

    @Override
    public InvasionOfRavnica copy() {
        return new InvasionOfRavnica(this);
    }
}

enum InvasionOfRavnicaPredicate implements Predicate<MageObject> {
    TRUE(true),
    FALSE(false);
    private final boolean value;

    InvasionOfRavnicaPredicate(boolean value) {
        this.value = value;
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        return (input.getColor(game).getColorCount() == 2) == value;
    }
}
