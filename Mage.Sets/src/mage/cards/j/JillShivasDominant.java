package mage.cards.j;

import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SagaAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileAndReturnSourceEffect;
import mage.abilities.effects.common.ExileSourceAndReturnFaceUpEffect;
import mage.abilities.effects.common.ReturnToHandTargetEffect;
import mage.abilities.effects.common.TapAllEffect;
import mage.abilities.effects.common.combat.CantBeBlockedTargetEffect;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.*;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterLandPermanent;
import mage.filter.common.FilterNonlandPermanent;
import mage.filter.predicate.mageobject.AnotherPredicate;
import mage.target.TargetPermanent;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class JillShivasDominant extends TransformingDoubleFacedCard {

    private static final FilterPermanent filterFront = new FilterNonlandPermanent("other target nonland permanent");
    private static final FilterPermanent filterLandsOpponents = new FilterLandPermanent("lands your opponents control");

    static {
        filterFront.add(AnotherPredicate.instance);
        filterLandsOpponents.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public JillShivasDominant(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo,
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.HUMAN, SubType.NOBLE, SubType.WARRIOR}, "{2}{U}",
                "Shiva, Warden of Ice",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.ENCHANTMENT, CardType.CREATURE}, new SubType[]{SubType.SAGA, SubType.ELEMENTAL}, "U");

        // Jill, Shiva's Dominant (front)
        this.getLeftHalfCard().setPT(2, 2);

        // When Jill enters, return up to one other target nonland permanent to its owner's hand.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ReturnToHandTargetEffect());
        ability.addTarget(new TargetPermanent(0, 1, filterFront));
        this.getLeftHalfCard().addAbility(ability);

        // {3}{U}{U}, {T}: Exile Jill, then return it to the battlefield transformed under its owner's control. Activate only as a sorcery.
        Ability transformAbility = new ActivateAsSorceryActivatedAbility(
                new ExileAndReturnSourceEffect(PutCards.BATTLEFIELD_TRANSFORMED), new ManaCostsImpl<>("{3}{U}{U}")
        );
        transformAbility.addCost(new TapSourceCost());
        this.getLeftHalfCard().addAbility(transformAbility);

        // Shiva, Warden of Ice (back)
        this.getRightHalfCard().setPT(4, 5);

        // (As this Saga enters and after your draw step, add a lore counter.)
        SagaAbility sagaAbility = new SagaAbility(this.getRightHalfCard());

        // I, II -- Mesmerize -- Target creature can't be blocked this turn.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_I, SagaChapter.CHAPTER_II, sagaChapterAbility -> {
            sagaChapterAbility.addEffect(new CantBeBlockedTargetEffect(Duration.EndOfTurn));
            sagaChapterAbility.addTarget(new TargetCreaturePermanent());
            sagaChapterAbility.withFlavorWord("Mesmerize");
        });

        // III -- Cold Snap -- Tap all lands your opponents control. Exile Shiva, then return it to the battlefield.
        sagaAbility.addChapterEffect(this.getRightHalfCard(), SagaChapter.CHAPTER_III, sagaChapterAbility -> {
            sagaChapterAbility.addEffect(new TapAllEffect(filterLandsOpponents));
            sagaChapterAbility.addEffect(new ExileSourceAndReturnFaceUpEffect());
            sagaChapterAbility.withFlavorWord("Cold Snap");
        });
        this.getRightHalfCard().addAbility(sagaAbility);
    }

    private JillShivasDominant(final JillShivasDominant card) {
        super(card);
    }

    @Override
    public JillShivasDominant copy() {
        return new JillShivasDominant(this);
    }
}
