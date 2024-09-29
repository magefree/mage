package mage.cards.h;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.ConditionHint;
import mage.abilities.hint.Hint;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.AdventureCard;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.game.Game;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class HowlingGalefang extends CardImpl {

    public HowlingGalefang(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{G}");

        this.subtype.add(SubType.BEAST);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Howling Galefang has haste as long as you own a card in exile that has an Adventure.
        this.addAbility(new SimpleStaticAbility(new ConditionalContinuousEffect(
                new GainAbilitySourceEffect(HasteAbility.getInstance()), HowlingGalefangCondition.instance,
                "{this} has haste as long as you own a card in exile that has an Adventure"
        )).addHint(HowlingGalefangCondition.getHint()));
    }

    private HowlingGalefang(final HowlingGalefang card) {
        super(card);
    }

    @Override
    public HowlingGalefang copy() {
        return new HowlingGalefang(this);
    }
}

enum HowlingGalefangCondition implements Condition {
    instance;
    private static final Hint hint = new ConditionHint(instance, "You have an Adventure in exile");

    @Override
    public boolean apply(Game game, Ability source) {
        return game
                .getExile()
                .getAllCards(game, source.getControllerId())
                .stream()
                .anyMatch(AdventureCard.class::isInstance);
    }

    public static Hint getHint() {
        return hint;
    }
}