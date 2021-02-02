package mage.cards.s;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.BeginningOfUpkeepTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.condition.InvertCondition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.abilities.effects.common.SacrificeControllerEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.filter.common.FilterCreaturePermanent;
import mage.filter.predicate.Predicates;
import mage.game.Game;
import mage.players.Player;
import mage.target.Target;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author LevelX2
 */
public final class ShadowbornDemon extends CardImpl {

    private static final FilterCreaturePermanent filter = new FilterCreaturePermanent("non-Demon creature");

    static {
        filter.add(Predicates.not(SubType.DEMON.getPredicate()));
    }

    public ShadowbornDemon(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}{B}");
        this.subtype.add(SubType.DEMON);

        this.power = new MageInt(5);
        this.toughness = new MageInt(6);

        // Flying
        this.addAbility(FlyingAbility.getInstance());
        // When Shadowborn Demon enters the battlefield, destroy target non-Demon creature.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect(), false);
        Target target = new TargetCreaturePermanent(filter);
        ability.addTarget(target);
        this.addAbility(ability);

        // At the beginning of your upkeep, if there are fewer than six creature cards in your graveyard, sacrifice a creature.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new BeginningOfUpkeepTriggeredAbility(new SacrificeControllerEffect(StaticFilters.FILTER_PERMANENT_CREATURE, 1, ""), TargetController.YOU, false),
                new InvertCondition(new CreatureCardsInControllerGraveyardCondition(6)),
                "At the beginning of your upkeep, if there are fewer than six creature cards in your graveyard, sacrifice a creature"));

    }

    private ShadowbornDemon(final ShadowbornDemon card) {
        super(card);
    }

    @Override
    public ShadowbornDemon copy() {
        return new ShadowbornDemon(this);
    }
}

class CreatureCardsInControllerGraveyardCondition implements Condition {

    private int value;

    public CreatureCardsInControllerGraveyardCondition(int value) {
        this.value = value;
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player p = game.getPlayer(source.getControllerId());
        if (p != null && p.getGraveyard().count(StaticFilters.FILTER_CARD_CREATURE, game) >= value) {
            return true;
        }
        return false;
    }
}
