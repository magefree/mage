package mage.cards.s;

import mage.MageInt;
import mage.abilities.common.SagaAbility;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class ShivaWardenOfIce extends CardImpl {

    private static final FilterPermanent filter = new FilterLandPermanent("lands your opponents control");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public ShivaWardenOfIce(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SAGA);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.nightCard = true;
        this.color.setBlue(true);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this);

        // I, II -- Mesmerize -- Target creature can't be blocked this turn.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, ability -> {
            ability.addEffect(new CantBeBlockedTargetEffect(Duration.EndOfTurn));
            ability.addTarget(new TargetCreaturePermanent());
            ability.withFlavorWord("Mesmerize");
        });

        // III -- Cold Snap -- Tap all lands your opponents control. Exile Shiva, then return it to the battlefield.
        sagaAbility.addChapterEffect(this, SagaChapter.CHAPTER_III, ability -> {
            ability.addEffect(new TapAllEffect(filter));
            ability.addEffect(new ExileSourceAndReturnFaceUpEffect());
            ability.withFlavorWord("Cold Snap");
        });
        this.addAbility(sagaAbility);
    }

    private ShivaWardenOfIce(final ShivaWardenOfIce card) {
        super(card);
    }

    @Override
    public ShivaWardenOfIce copy() {
        return new ShivaWardenOfIce(this);
    }
}
