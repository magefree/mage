package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksEachCombatStaticAbility;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.SacrificeSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.MayhemAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.Card;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.*;
import mage.filter.FilterCard;
import mage.filter.predicate.mageobject.ManaValuePredicate;
import mage.game.Game;
import mage.game.permanent.Permanent;
import mage.players.Player;
import mage.target.common.TargetCardInYourGraveyard;
import mage.target.targetpointer.FixedTarget;
import mage.util.CardUtil;

import java.util.UUID;

/**
 *
 * @author Jmlundeen
 */
public final class CarnageCrimsonChaos extends CardImpl {

    private static final FilterCard filter = new FilterCard("creature card with mana value 3 or less");

    static {
        filter.add(new ManaValuePredicate(ComparisonType.OR_LESS, 3));
    }

    public CarnageCrimsonChaos(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}{R}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.SYMBIOTE);
        this.subtype.add(SubType.VILLAIN);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Carnage enters, return target creature card with mana value 3 or less from your graveyard to the battlefield. It gains "This creature attacks each combat if able" and "When this creature deals combat damage to a player, sacrifice it."
        Ability ability = new EntersBattlefieldTriggeredAbility(new CarnageCrimsonChaosReturnEffect());
        ability.addTarget(new TargetCardInYourGraveyard(filter));
        this.addAbility(ability);


        // Mayhem {B}{R}
        this.addAbility(new MayhemAbility(this, "{B}{R}"));

    }

    private CarnageCrimsonChaos(final CarnageCrimsonChaos card) {
        super(card);
    }

    @Override
    public CarnageCrimsonChaos copy() {
        return new CarnageCrimsonChaos(this);
    }
}

class CarnageCrimsonChaosReturnEffect extends OneShotEffect {

    CarnageCrimsonChaosReturnEffect() {
        super(Outcome.PutCreatureInPlay);
        this.staticText = "return target creature card with mana value 3 or less from your graveyard to the battlefield. It gains \"This creature attacks each combat if able\" and \"When this creature deals combat damage to a player, sacrifice it.\"";
    }

    protected CarnageCrimsonChaosReturnEffect(final CarnageCrimsonChaosReturnEffect effect) {
        super(effect);
    }

    @Override
    public CarnageCrimsonChaosReturnEffect copy() {
        return new CarnageCrimsonChaosReturnEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player controller = game.getPlayer(source.getControllerId());
        if (controller == null) {
            return false;
        }

        Card card = game.getCard(source.getFirstTarget());
        if (card == null) {
            return false;
        }
        controller.moveCards(card, Zone.BATTLEFIELD, source, game);
        Permanent permanent = CardUtil.getPermanentFromCardPutToBattlefield(card, game);
        if (permanent == null) {
            return false;
        }
        Ability attacksEachTurnAbility = new AttacksEachCombatStaticAbility();
        Ability damageTriggerAbility = new DealsCombatDamageToAPlayerTriggeredAbility(new SacrificeSourceEffect());
        ContinuousEffect effectOne = new GainAbilityTargetEffect(attacksEachTurnAbility, Duration.WhileOnBattlefield);
        effectOne.setTargetPointer(new FixedTarget(permanent, game));
        ContinuousEffect effectTwo = new GainAbilityTargetEffect(damageTriggerAbility, Duration.WhileOnBattlefield);
        effectTwo.setTargetPointer(new FixedTarget(permanent, game));
        game.addEffect(effectOne, source);
        game.addEffect(effectTwo, source);
        return true;
    }
}
