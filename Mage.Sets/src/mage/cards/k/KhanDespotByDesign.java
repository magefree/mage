package mage.cards.k;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.DamageAllEffect;
import mage.abilities.effects.common.DamagePlayersEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TargetController;
import mage.filter.StaticFilters;
import mage.game.Game;

import java.util.UUID;

/**
 * @author muz
 */
public final class KhanDespotByDesign extends CardImpl {

    public KhanDespotByDesign(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Trample
        this.addAbility(TrampleAbility.getInstance());

        // When Khan enters, he deals 1 damage to each opponent and each creature your opponents control.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new KhanDespotByDesignEffect()));
    }

    private KhanDespotByDesign(final KhanDespotByDesign card) {
        super(card);
    }

    @Override
    public KhanDespotByDesign copy() {
        return new KhanDespotByDesign(this);
    }
}

class KhanDespotByDesignEffect extends OneShotEffect {

    KhanDespotByDesignEffect() {
        super(Outcome.Damage);
        staticText = "{this} deals 1 damage to each opponent and each creature your opponents control";
    }

    private KhanDespotByDesignEffect(final KhanDespotByDesignEffect effect) {
        super(effect);
    }

    @Override
    public KhanDespotByDesignEffect copy() {
        return new KhanDespotByDesignEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        new DamagePlayersEffect(1, TargetController.OPPONENT).apply(game, source);
        new DamageAllEffect(1, StaticFilters.FILTER_OPPONENTS_PERMANENT_CREATURE).apply(game, source);
        return true;
    }
}
