package mage.cards.v;

import mage.MageInt;
import mage.MageObject;
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
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.ObjectSourcePlayer;
import mage.filter.predicate.ObjectSourcePlayerPredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.game.permanent.token.SaprolingToken;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VerdelothTheAncient extends CardImpl {

    private static final FilterCreaturePermanent filter
            = new FilterCreaturePermanent("Saproling creatures and other Treefolk creatures");

    static {
        filter.add(VerdelothTheAncientPredicate.instance);
    }

    public VerdelothTheAncient(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{G}{G}");
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.TREEFOLK);

        this.power = new MageInt(4);
        this.toughness = new MageInt(7);

        // Kicker {X}
        this.addAbility(new KickerAbility("{X}"));

        // Saproling creatures and other Treefolk creatures get +1/+1.
        this.addAbility(new SimpleStaticAbility(new BoostAllEffect(
                1, 1, Duration.WhileOnBattlefield, filter, false
        )));

        // When Verdeloth the Ancient enters the battlefield, if it was kicked, create X 1/1 green Saproling creature tokens.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(new EntersBattlefieldTriggeredAbility(
                new CreateTokenEffect(new SaprolingToken(), GetKickerXValue.instance), false
        ), KickedCondition.ONCE, "When {this} enters the battlefield, " +
                "if it was kicked, create X 1/1 green Saproling creature tokens."));
    }

    private VerdelothTheAncient(final VerdelothTheAncient card) {
        super(card);
    }

    @Override
    public VerdelothTheAncient copy() {
        return new VerdelothTheAncient(this);
    }
}

enum VerdelothTheAncientPredicate implements ObjectSourcePlayerPredicate<Permanent> {
    instance;

    @Override
    public boolean apply(ObjectSourcePlayer<Permanent> input, Game game) {
        MageObject obj = input.getObject();
        if (obj.getId().equals(input.getSourceId())) {
            return obj.hasSubtype(SubType.SAPROLING, game);
        }
        return obj.hasSubtype(SubType.TREEFOLK, game)
                || obj.hasSubtype(SubType.SAPROLING, game);
    }
}
