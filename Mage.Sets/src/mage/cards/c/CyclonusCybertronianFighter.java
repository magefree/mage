package mage.cards.c;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.LivingMetalAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.TurnPhase;
import mage.game.Game;
import mage.game.turn.TurnMod;

import java.util.UUID;

/**
 * @author mllagostera
 */
public final class CyclonusCybertronianFighter extends CardImpl {

    public CyclonusCybertronianFighter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT}, "");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.VEHICLE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setBlack(true);
        this.color.setBlue(true);
        this.nightCard = true;

        // Living metal
        this.addAbility(new LivingMetalAbility());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Cyclonus deals combat damage to a player, convert it.
        // If you do, there is an additional beginning phase after this phase.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(
            new CyclonusCybertronianFighterEffect(),
             false
            );
        this.addAbility(ability);
    }

    private CyclonusCybertronianFighter(final CyclonusCybertronianFighter card) {
        super(card);
    }

    @Override
    public CyclonusCybertronianFighter copy() {
        return new CyclonusCybertronianFighter(this);
    }
}

class CyclonusCybertronianFighterEffect extends TransformSourceEffect {

    CyclonusCybertronianFighterEffect() {
        super();
        staticText = "transform it. If you do, there is an additional beginning phase after this phase";
    }

    private CyclonusCybertronianFighterEffect(final CyclonusCybertronianFighterEffect effect) {
        super(effect);
    }

    @Override
    public CyclonusCybertronianFighterEffect copy() {
        return new CyclonusCybertronianFighterEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        if (!super.apply(game, source)) {
            return false;
        }
        TurnMod beginning = new TurnMod(game.getState().getActivePlayerId()).withExtraPhase(TurnPhase.BEGINNING);
        game.getState().getTurnMods().add(beginning);
        return true;
    }
}
