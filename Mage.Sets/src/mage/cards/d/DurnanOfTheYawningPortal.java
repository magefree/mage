
package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.ChooseABackgroundAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.costs.CostAdjuster;
import mage.abilities.dynamicvalue.common.OpponentsCount;
import mage.abilities.effects.common.InfoEffect;
import mage.abilities.effects.common.LookLibraryAndPickControllerEffect;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.cards.Cards;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.PutCards;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.constants.Zone;
import mage.filter.FilterCard;
import mage.game.Game;
import mage.players.Player;
import mage.util.CardUtil;
import mage.abilities.effects.common.asthought.PlayFromNotOwnHandZoneTargetEffect;

/**
 *
 * @author Zelane
 */
public final class DurnanOfTheYawningPortal extends CardImpl {

    public DurnanOfTheYawningPortal(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[] { CardType.CREATURE }, "{3}{G}");

        this.addSuperType(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WARRIOR);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        Ability ability = new AttacksTriggeredAbility(new ExileEffect());
        this.addAbility(ability);

        // Choose a Background
        this.addAbility(ChooseABackgroundAbility.getInstance());
    }

    private DurnanOfTheYawningPortal(final DurnanOfTheYawningPortal card) {
        super(card);
    }

    @Override
    public DurnanOfTheYawningPortal copy() {
        return new DurnanOfTheYawningPortal(this);
    }
}

class ExileEffect extends LookLibraryAndPickControllerEffect {

    private static final FilterCard filter = new FilterCard("a creature");
    static {
        filter.add(CardType.CREATURE.getPredicate());
    }

    ExileEffect() {
        super(4, 1, filter, PutCards.EXILED, PutCards.BOTTOM_ANY, true);
    }

    private ExileEffect(final ExileEffect effect) {
        super(effect);
    }

    @Override
    public ExileEffect copy() {
        return new ExileEffect(this);
    }

    @Override
    protected boolean actionWithPickedCards(Game game, Ability source, Player player, Cards pickedCards,
            Cards otherCards) {
        boolean result = true;
        if (pickedCards.size() > 0) {
            Card card = game.getCard(pickedCards.iterator().next());
            UndauntedAdjuster.addAdjusterAndMessage(card.getMainCard());
            result = PlayFromNotOwnHandZoneTargetEffect.exileAndPlayFromExile(game, source, card, TargetController.YOU,
                    Duration.Custom, false, false, true);
        }

        result |= putLookedCards.moveCards(player, otherCards, source, game);
        return result;
    }

    @Override
    public String getText(Mode mode) {
        return super.getText(mode)
                .concat(". For as long as that card remains exiled, you may cast it. That spell has undaunted.");
    }
}

class UndauntedAdjuster implements CostAdjuster {
    private UndauntedAdjuster() {
        super();
    }

    @Override
    public void adjustCosts(Ability ability, Game game) {
        if (game.inCheckPlayableState()) {
            return;
        }
        Integer opponents = OpponentsCount.instance.calculate(game, ability, null);
        CardUtil.reduceCost(ability, opponents);
    }

    public static final void addAdjusterAndMessage(Card card) {
        card.addAbility(new SimpleStaticAbility(
                Zone.ALL,
                new InfoEffect("undaunted <i>(This spell costs {1} less to cast for each opponent.)</i>"))
                .setRuleAtTheTop(true));
        card.getSpellAbility().setCostAdjuster(new UndauntedAdjuster());
    }
}
