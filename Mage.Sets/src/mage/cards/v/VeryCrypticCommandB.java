
package mage.cards.v;

import mage.MageObjectReference;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.effects.Effect;

import mage.abilities.effects.common.UntapTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.target.TargetPermanent;

import mage.abilities.effects.common.TapAllEffect;
import mage.filter.common.FilterPermanent;
import mage.filter.predicate.Predicates;
import mage.MageInt;
import mage.MageObject;
import mage.cards.ModalDoubleFacedCard;
import mage.cards.SplitCard;
import mage.game.Game;
import mage.game.stack.Spell;
import java.util.UUID;
import java.util.regex.Pattern;

import mage.abilities.effects.common.discard.DiscardHandDrawSameNumberSourceEffect;

import mage.abilities.effects.common.ReturnFromGraveyardToHandTargetEffect;
import mage.filter.StaticFilters;
import mage.target.common.TargetCardInYourGraveyard;

/**
 *
 * @author tiera3
 * based on VeryCrypticCommandD,DreamsGrip,HopeAndGlory,DoubleHeader,AetherShockwave,TolarianWinds,CallToMind
 */
public final class VeryCrypticCommandB extends CardImpl {

    private static final FilterPermanent filter = new FilterPermanent("permanent with a one-word name");

    static {
        filter.add(new OneWordPredicate());
    }

    public VeryCrypticCommandB(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.INSTANT}, "{1}{U}{U}{U}");

        // Choose two - 
        this.getSpellAbility().getModes().setMinModes(2);
        this.getSpellAbility().getModes().setMaxModes(2);

        // Untap two target permanents.
        this.getSpellAbility().addEffect(new UntapTargetEffect());
        this.getSpellAbility().addTarget(new TargetPermanent(2));

        // Tap each permanent target player controls with exactly one word in its name.
        Mode mode = new Mode(new TapAllEffect(filter));
        this.getSpellAbility().getModes().addMode(mode);

        // Discard all the cards in your hand, then draw that many cards.
        mode = new Mode(new DiscardHandDrawSameNumberSourceEffect());
        this.getSpellAbility().getModes().addMode(mode);

        // Return target instant or sorcery card from your graveyard to your hand.
        mode = new Mode(new ReturnFromGraveyardToHandTargetEffect());
        mode.addTarget(new TargetCardInYourGraveyard(StaticFilters.FILTER_CARD_INSTANT_OR_SORCERY_FROM_YOUR_GRAVEYARD));
        this.getSpellAbility().getModes().addMode(mode);
    }

    private VeryCrypticCommandB(final VeryCrypticCommandB card) {
        super(card);
    }

    @Override
    public VeryCrypticCommandB copy() {
        return new VeryCrypticCommandB(this);
    }
}

class OneWordPredicate implements Predicate<MageObject> {

    public OneWordPredicate() {
    }

    @Override
    public boolean apply(MageObject input, Game game) {
        String name = input.getName();
        if (input instanceof SplitCard) {
            return hasOneWord(((SplitCard) input).getLeftHalfCard().getName()) || hasOneWord(((SplitCard) input).getRightHalfCard().getName());
        } else if (input instanceof ModalDoubleFacedCard) {
            return hasOneWord(((ModalDoubleFacedCard) input).getLeftHalfCard().getName()) || hasOneWord(((ModalDoubleFacedCard) input).getRightHalfCard().getName());
        } else if (input instanceof Spell && ((Spell) input).getSpellAbility().getSpellAbilityType() == SpellAbilityType.SPLIT_FUSED) {
            SplitCard card = (SplitCard) ((Spell) input).getCard();
            return hasOneWord(card.getLeftHalfCard().getName()) || hasOneWord(card.getRightHalfCard().getName());
        } else {
            if (name.contains(" // ")) {
                String leftName = name.substring(0, name.indexOf(" // "));
                String rightName = name.substring(name.indexOf(" // ") + 4);
                return hasOneWord(leftName) || hasOneWord(rightName);
            } else {
                return hasOneWord(name);
            }
        }
    }

    private boolean hasOneWord(String str) {
        return Pattern.compile("\\s+").split(str).length == 1;
    }

    @Override
    public String toString() {
        return "";
    }
}
