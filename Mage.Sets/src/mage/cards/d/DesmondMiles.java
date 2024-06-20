package mage.cards.d;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.dynamicvalue.DynamicValue;
import mage.abilities.dynamicvalue.common.AssassinsControlledAndInGraveCount;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.dynamicvalue.common.StaticValue;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.continuous.BoostSourceEffect;
import mage.abilities.effects.keyword.ScryEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.constants.*;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.game.Game;
import mage.game.permanent.Permanent;

/**
 *
 * @author grimreap124
 */
public final class DesmondMiles extends CardImpl {

    public DesmondMiles(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ASSASSIN);
        this.power = new MageInt(1);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Desmond Miles gets +1/+0 for each other Assassin you control and each Assassin card in your graveyard.
        Ability ability = new SimpleStaticAbility(new BoostSourceEffect(AssassinsControlledAndInGraveCount.FOR_EACH, StaticValue.get(0), Duration.WhileOnBattlefield));
        this.addAbility(ability);
        // Whenever Desmond Miles deals combat damage to a player, surveil X, where X is the amount of damage it dealt to that player.
        this.addAbility(new DealsCombatDamageToAPlayerTriggeredAbility(new DesmondMilesEffect(SavedDamageValue.MANY)));
    }

    private DesmondMiles(final DesmondMiles card) {
        super(card);
    }

    @Override
    public DesmondMiles copy() {
        return new DesmondMiles(this);
    }
}

class DesmondMilesEffect extends OneShotEffect {

    private final DynamicValue amount;

    DesmondMilesEffect(DynamicValue amount) {
        super(Outcome.Benefit);
        this.amount = amount;
        staticText = "surveil X, where X is the amount of damage it dealt to that player";
    }

    private DesmondMilesEffect(final DesmondMilesEffect effect) {
        super(effect);
        this.amount = effect.amount;
    }

    @Override
    public DesmondMilesEffect copy() {
        return new DesmondMilesEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        int xValue = amount.calculate(game, source, this);
        return new SurveilEffect(xValue).apply(game, source);
    }
}