package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.ObjectColor;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.condition.InvertCondition;
import mage.abilities.condition.common.SourceTappedCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.UntapSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProtectionAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.mageobject.ColorPredicate;


/**
 *
 * @author LevelX2
 */
public final class PristineAngel extends CardImpl {

    private static final FilterCard filter = new FilterCard("artifacts and all colors");

    static {
        filter.add(Predicates.or(
                CardType.ARTIFACT.getPredicate(),
                new ColorPredicate(ObjectColor.BLACK),
                new ColorPredicate(ObjectColor.BLUE),
                new ColorPredicate(ObjectColor.GREEN),
                new ColorPredicate(ObjectColor.RED),
                new ColorPredicate(ObjectColor.WHITE)));
    }

    public PristineAngel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{4}{W}{W}");
        this.subtype.add(SubType.ANGEL);

        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // As long as Pristine Angel is untapped, it has protection from artifacts and from all colors.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD,
                new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(new ProtectionAbility(filter), Duration.WhileOnBattlefield),
                SourceTappedCondition.UNTAPPED,
                "As long as {this} is untapped, it has protection from artifacts and from all colors")));
        // Whenever you cast a spell, you may untap Pristine Angel.
        this.addAbility(new SpellCastControllerTriggeredAbility(new UntapSourceEffect(), true));
    }

    private PristineAngel(final PristineAngel card) {
        super(card);
    }

    @Override
    public PristineAngel copy() {
        return new PristineAngel(this);
    }
}
