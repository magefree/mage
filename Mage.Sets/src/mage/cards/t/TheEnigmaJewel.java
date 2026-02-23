package mage.cards.t;

import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.keyword.CraftAbility;
import mage.abilities.mana.ConditionalColorlessManaAbility;
import mage.abilities.mana.builder.common.ActivatedAbilityManaBuilder;
import mage.cards.Card;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.filter.predicate.Predicate;
import mage.filter.predicate.other.NotManaAbilityPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class TheEnigmaJewel extends TransformingDoubleFacedCard {

    private static final FilterStackObject filter = new FilterActivatedOrTriggeredAbility("an ability that isn't a mana ability");

    static {
        filter.add(NotManaAbilityPredicate.instance);
    }

    public TheEnigmaJewel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "{U}",
                "Locus of Enlightenment",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ARTIFACT}, new SubType[]{}, "U"
        );

        // The Enigma Jewel
        // The Enigma Jewel enters the battlefield tapped.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTappedAbility());

        // {T}: Add {C}{C}. Spend this mana only to activate abilities.
        this.getLeftHalfCard().addAbility(new ConditionalColorlessManaAbility(2, new ActivatedAbilityManaBuilder()));

        // Craft with four or more nonlands with activated abilities {8}{U}
        this.getLeftHalfCard().addAbility(new CraftAbility(
                "{8}{U}", "four or more nonlands with activated abilities", "other " +
                "nonland permanents you control with activated abilities and/or nonland cards in your " +
                "graveyard with activated abilities", 4, Integer.MAX_VALUE, TheEnigmaJewelPredicate.instance
        ));

        // Locus of Enlightenment
        // Locus of Enlightenment has each activated ability of the exiled cards used to craft it. You may activate each of those abilities only once each turn.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(new LocusOfEnlightenmentEffect()));

        // Whenever you activate an ability that isn't a mana ability, copy it. You may choose new targets for the copy.
        this.getRightHalfCard().addAbility(new ActivateAbilityTriggeredAbility(new CopyStackObjectEffect("it"), filter, SetTargetPointer.SPELL));
    }

    private TheEnigmaJewel(final TheEnigmaJewel card) {
        super(card);
    }

    @Override
    public TheEnigmaJewel copy() {
        return new TheEnigmaJewel(this);
    }
}

enum TheEnigmaJewelPredicate implements Predicate<MageObject> {
    instance;

    @Override
    public boolean apply(MageObject input, Game game) {
        return !input.isLand(game)
                && input instanceof Card
                && ((Card) input).getAbilities(game)
                .stream()
                .anyMatch(a -> (a.isActivatedAbility()));
    }
}

class LocusOfEnlightenmentEffect extends ContinuousEffectImpl {

    LocusOfEnlightenmentEffect() {
        super(Duration.WhileOnBattlefield, Layer.AbilityAddingRemovingEffects_6, SubLayer.NA, Outcome.Benefit);
        staticText = "{this} has each activated ability of the exiled cards " +
                "used to craft it. You may activate each of those abilities only once each turn";
    }

    private LocusOfEnlightenmentEffect(final LocusOfEnlightenmentEffect effect) {
        super(effect);
    }

    @Override
    public LocusOfEnlightenmentEffect copy() {
        return new LocusOfEnlightenmentEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Permanent permanent = source.getSourcePermanentIfItStillExists(game);
        if (permanent == null) {
            return false;
        }
        ExileZone exileZone = game
                .getExile()
                .getExileZone(CardUtil.getExileZoneId(game, permanent.getMainCard().getId(),
                        permanent.getMainCard().getZoneChangeCounter(game) - 1));
        if (exileZone == null) {
            return false;
        }
        for (Card card : exileZone.getCards(game)) {
            for (Ability ability : card.getAbilities(game)) {
                if (ability.isActivatedAbility()) {
                    ActivatedAbility copyAbility = (ActivatedAbility) ability.copy();
                    copyAbility.setMaxActivationsPerTurn(1);
                    permanent.addAbility(copyAbility, source.getSourceId(), game, true);
                }
            }
        }
        return true;
    }
}
