package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.CastSecondSpellTriggeredAbility;
import mage.abilities.common.delayed.ReflexiveTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.Effect;
import mage.abilities.effects.OneShotEffect;
import mage.abilities.effects.common.CopyStackObjectEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.DoIfCostPaid;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Outcome;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.filter.StaticFilters;
import mage.game.Game;
import mage.game.stack.Spell;
import mage.players.Player;
import mage.target.common.TargetAnyTarget;

import java.util.Optional;
import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BreechesTheBlastmaker extends CardImpl {

    public BreechesTheBlastmaker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.GOBLIN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Menace
        this.addAbility(new MenaceAbility());

        // Whenever you cast your second spell each turn, you may sacrifice an artifact. If you do, flip a coin. When you win the flip, copy that spell. You may choose new targets for the copy. When you lose the flip, Breeches, the Blastmaker deals damage equal to that spell's mana value to any target.
        this.addAbility(new CastSecondSpellTriggeredAbility(new DoIfCostPaid(
                new BreechesTheBlastmakerEffect(), new SacrificeTargetCost(StaticFilters.FILTER_PERMANENT_ARTIFACT_AN)
        )));
    }

    private BreechesTheBlastmaker(final BreechesTheBlastmaker card) {
        super(card);
    }

    @Override
    public BreechesTheBlastmaker copy() {
        return new BreechesTheBlastmaker(this);
    }
}

class BreechesTheBlastmakerEffect extends OneShotEffect {

    BreechesTheBlastmakerEffect() {
        super(Outcome.Benefit);
        staticText = "flip a coin. When you win the flip, copy that spell. You may choose new targets for the copy. " +
                "When you lose the flip, {this} deals damage equal to that spell's mana value to any target";
    }

    private BreechesTheBlastmakerEffect(final BreechesTheBlastmakerEffect effect) {
        super(effect);
    }

    @Override
    public BreechesTheBlastmakerEffect copy() {
        return new BreechesTheBlastmakerEffect(this);
    }

    @Override
    public boolean apply(Game game, Ability source) {
        Player player = game.getPlayer(source.getControllerId());
        if (player == null) {
            return false;
        }
        Spell spell = (Spell) getValue("spellCast");
        ReflexiveTriggeredAbility ability;
        if (player.flipCoin(source, game, true)) {
            Effect effect = new CopyStackObjectEffect();
            effect.setText("copy that spell. You may choose new targets for the copy");
            effect.setValue("stackObject", spell);
            ability = new ReflexiveTriggeredAbility(effect, false);
        } else {
            int mv = Optional
                    .ofNullable(spell)
                    .map(Spell::getManaValue)
                    .orElse(0);
            ability = new ReflexiveTriggeredAbility(new DamageTargetEffect(mv), false);
            ability.addTarget(new TargetAnyTarget());
        }
        game.fireReflexiveTriggeredAbility(ability, source);
        return true;
    }
}
