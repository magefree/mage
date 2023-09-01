package mage.cards.i;

import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SiegeAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardSetInfo;
import mage.cards.TransformingDoubleFacedCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.game.permanent.token.Kraken11Token;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class InvasionOfSegovia extends TransformingDoubleFacedCard {

    private static final FilterCard filter = new FilterCard("noncreature spells you cast");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(Predicates.not(new AbilityPredicate(ConvokeAbility.class))); // So there are not redundant copies being added to each card
    }

    public InvasionOfSegovia(UUID ownerId, CardSetInfo setInfo) {
        super(
                ownerId, setInfo,
                new SuperType[]{}, new CardType[]{CardType.BATTLE}, new SubType[]{SubType.SIEGE}, "{2}{U}",
                "Caetus, Sea Tyrant of Segovia",
                new SuperType[]{SuperType.LEGENDARY}, new CardType[]{CardType.CREATURE}, new SubType[]{SubType.SERPENT}, "U"
        );
        this.getLeftHalfCard().setStartingDefense(4);
        this.getRightHalfCard().setPT(3, 3);

        // (As a Siege enters, choose an opponent to protect it. You and others can attack it. When it's defeated, exile it, then cast it transformed.)
        this.getLeftHalfCard().addAbility(new SiegeAbility());

        // When Invasion of Segovia enters the battlefield, create two 1/1 blue Kraken creature tokens with trample.
        this.getLeftHalfCard().addAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new Kraken11Token(), 2)
        ));

        // Caetus, Sea Tyrant of Segovia
        // Noncreature spells you cast have convoke.
        this.getRightHalfCard().addAbility(new SimpleStaticAbility(
                new GainAbilityControlledSpellsEffect(new ConvokeAbility(), filter)
        ));

        // At the beginning of your end step, untap up to four target creatures.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new UntapTargetEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetCreaturePermanent(0, 4));
        this.getRightHalfCard().addAbility(ability);
    }

    private InvasionOfSegovia(final InvasionOfSegovia card) {
        super(card);
    }

    @Override
    public InvasionOfSegovia copy() {
        return new InvasionOfSegovia(this);
    }
}
