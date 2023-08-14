package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfEndStepTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.UntapTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledSpellsEffect;
import mage.abilities.keyword.ConvokeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.AbilityPredicate;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class CaetusSeaTyrantOfSegovia extends CardImpl {

    private static final FilterCard filter = new FilterCard("noncreature spells you cast");

    static {
        filter.add(Predicates.not(CardType.CREATURE.getPredicate()));
        filter.add(Predicates.not(CardType.LAND.getPredicate()));
        filter.add(Predicates.not(new AbilityPredicate(ConvokeAbility.class))); // So there are not redundant copies being added to each card
    }

    public CaetusSeaTyrantOfSegovia(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SERPENT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.color.setBlue(true);
        this.nightCard = true;

        // Noncreature spells you cast have convoke.
        this.addAbility(new SimpleStaticAbility(new GainAbilityControlledSpellsEffect(new ConvokeAbility(), filter)));

        // At the beginning of your end step, untap up to four target creatures.
        Ability ability = new BeginningOfEndStepTriggeredAbility(
                new UntapTargetEffect(), TargetController.YOU, false
        );
        ability.addTarget(new TargetCreaturePermanent(0, 4));
        this.addAbility(ability);
    }

    private CaetusSeaTyrantOfSegovia(final CaetusSeaTyrantOfSegovia card) {
        super(card);
    }

    @Override
    public CaetusSeaTyrantOfSegovia copy() {
        return new CaetusSeaTyrantOfSegovia(this);
    }
}
