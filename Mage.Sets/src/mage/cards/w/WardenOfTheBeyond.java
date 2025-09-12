package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.keyword.VigilanceAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.game.Game;
import mage.players.Player;

import java.util.UUID;

/**
 *
 * @author LevelX2
 */
public final class WardenOfTheBeyond extends CardImpl {

    public WardenOfTheBeyond(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Warden of the Beyond gets +2/+2 as long as an opponent owns a card in exile.
        this.addAbility(new SimpleStaticAbility(
                new ConditionalContinuousEffect(new BoostSourceEffect(2, 2, Duration.WhileOnBattlefield), WardenOfTheBeyondCondition.instance,
                        "{this} gets +2/+2 as long as an opponent owns a card in exile")));
    }

    private WardenOfTheBeyond(final WardenOfTheBeyond card) {
        super(card);
    }

    @Override
    public WardenOfTheBeyond copy() {
        return new WardenOfTheBeyond(this);
    }
}

enum WardenOfTheBeyondCondition implements Condition {

    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller != null) {
            for (Card card : game.getExile().getCardsInRange(game, controller.getId())) {
                if (controller.hasOpponent(card.getOwnerId(), game)) {
                    return true;
                }
            }
        }
        return false;
    }
}
