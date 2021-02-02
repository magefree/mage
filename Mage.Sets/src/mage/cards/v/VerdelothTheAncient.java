package mage.cards.v;

import mage.MageInt;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.KickedCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.dynamicvalue.common.GetKickerXValue;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.BoostAllEffect;
import mage.abilities.keyword.KickerAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.filter.predicate.permanent.PermanentIdPredicate;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VerdelothTheAncient extends CardImpl {

    public VerdelothTheAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Kicker {X}
        this.addAbility(new KickerAbility("{X}"));

        // Saproling creatures and other Treefolk creatures get +1/+1.
        FilterCreaturePermanent filter = new FilterCreaturePermanent("Saproling creatures and other Treefolk creatures");
        filter.add(Predicates.or(
                Predicates.and(SubType.TREEFOLK.getPredicate(), Predicates.not(new PermanentIdPredicate(this.getId()))),
                SubType.SAPROLING.getPredicate())
        );
        filter.add(Predicates.not(new PermanentIdPredicate(this.getId())));

        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new BoostAllEffect(1, 1, Duration.WhileOnBattlefield, filter, false)));

        // When Verdeloth the Ancient enters the battlefield, if it was kicked, create X 1/1 green Saproling creature tokens.
        EntersBattlefieldTriggeredAbility ability = new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new SaprolingToken(), GetKickerXValue.instance), false);
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(ability, KickedCondition.instance,
                "When {this} enters the battlefield, if it was kicked, create X 1/1 green Saproling creature tokens."));

    }

    private VerdelothTheAncient(final VerdelothTheAncient card) {
        super(card);
    }

    @Override
    public VerdelothTheAncient copy() {
        return new VerdelothTheAncient(this);
    }
}