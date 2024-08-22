package mage.cards.l;

import mage.abilities.Ability;
import mage.abilities.ActivatedAbility;
import mage.abilities.common.ActivateAbilityTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.ContinuousEffectImpl;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterStackObject;
import mage.filter.common.FilterActivatedOrTriggeredAbility;
import mage.filter.predicate.other.NotManaAbilityPredicate;
import mage.game.ExileZone;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class LocusOfEnlightenment extends CardImpl {
    private static final FilterStackObject filter = new FilterActivatedOrTriggeredAbility("an ability that isn't a mana ability");

    static {
        filter.add(NotManaAbilityPredicate.instance);
    }
    public LocusOfEnlightenment(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.nightCard = true;
        this.color.setBlue(true);

        // Locus of Enlightenment has each activated ability of the exiled cards used to craft it. You may activate each of those abilities only once each turn.
        this.addAbility(new SimpleStaticAbility(new LocusOfEnlightenmentEffect()));

        // Whenever you activate an ability that isn't a mana ability, copy it. You may choose new targets for the copy.
        this.addAbility(new ActivateAbilityTriggeredAbility(new CopyStackObjectEffect("it"), filter, SetTargetPointer.SPELL));
    }

    private LocusOfEnlightenment(final LocusOfEnlightenment card) {
        super(card);
    }

    @Override
    public LocusOfEnlightenment copy() {
        return new LocusOfEnlightenment(this);
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
                .getExileZone(CardUtil.getExileZoneId(
                        game, permanent.getId(), permanent.getZoneChangeCounter(game) - 2
                ));
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
