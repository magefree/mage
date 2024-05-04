package mage.cards.s;

import mage.MageInt;
import mage.MageObject;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.condition.Condition;
import mage.abilities.decorator.ConditionalInterveningIfTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.effects.common.MayCastTargetCardEffect;
import mage.abilities.keyword.FlashAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.FilterSpell;
import mage.game.ExileZone;
import mage.game.Game;
import mage.players.Player;
import mage.target.TargetCard;
import mage.target.TargetSpell;
import mage.target.common.TargetCardInExile;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 * @author Susucr
 */
public final class SmirkingSpelljacker extends CardImpl {

    private static final FilterSpell filter = new FilterSpell("spell an opponent controls");

    static {
        filter.add(TargetController.OPPONENT.getControllerPredicate());
    }

    public SmirkingSpelljacker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.DJINN);
        this.subtype.add(SubType.WIZARD);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Flash
        this.addAbility(FlashAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // When Smirking Spelljacker enters the battlefield, exile target spell an opponent controls.
        Ability ability = new EntersBattlefieldTriggeredAbility(new ExileTargetEffect().setToSourceExileZone(true));
        ability.addTarget(new TargetSpell(filter));
        this.addAbility(ability);

        // Whenever Smirking Spelljacker attacks, if a card is exiled with it, you may cast the exiled card without paying its mana cost.
        this.addAbility(new ConditionalInterveningIfTriggeredAbility(
                new AttacksTriggeredAbility(new SmirkingSpelljackerEffect()),
                SmirkingSpelljackerCondition.instance,
                "Whenever {this} attacks, if a card is exiled with it, "
                        + "you may cast the exiled card without paying its mana cost."
        ));
    }

    private SmirkingSpelljacker(final SmirkingSpelljacker card) {
        super(card);
    }

    @Override
    public SmirkingSpelljacker copy() {
        return new SmirkingSpelljacker(this);
    }
}

enum SmirkingSpelljackerCondition implements Condition {
    instance;

    @Override
    public boolean apply(Game game, Ability source) {
        MageObject sourceObject = source.getSourceObject(game);
        ExileZone exile = game.getExile().getExileZone(CardUtil.getExileZoneId(game, sourceObject.getId(), sourceObject.getZoneChangeCounter(game)));
        return exile != null && !exile.isEmpty();
    }

    @Override
    public String toString() {
        return "if a card is exiled with it";
    }
}

class SmirkingSpelljackerEffect extends OneShotEffect {

    SmirkingSpelljackerEffect() {
        super(Outcome.PlayForFree);
        staticText = "you may cast the exiled card without paying its mana cost";
    }

    private SmirkingSpelljackerEffect(final SmirkingSpelljackerEffect effect) {
        super(effect);
    }

    @Override
    public SmirkingSpelljackerEffect copy() {
        return new SmirkingSpelljackerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }
        FilterCard filter = new FilterCard("card exiled with " + CardUtil.getSourceLogName(game, source));
        TargetCard target = new TargetCardInExile(1, 1, filter, CardUtil.getExileZoneId(game, source));
        target.withNotTarget(true);
        controller.choose(Outcome.PlayForFree, target, source, game);
        new MayCastTargetCardEffect(CastManaAdjustment.WITHOUT_PAYING_MANA_COST)
                .setTargetPointer(new FixedTarget(target.getFirstTarget()))
                .apply(game, source);
        return true;
    }

}