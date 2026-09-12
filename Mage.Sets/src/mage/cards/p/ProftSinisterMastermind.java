package mage.cards.p;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.constants.Zone;
import mage.game.Game;
import mage.game.events.GameEvent;
import mage.target.common.TargetCreaturePermanent;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.ThresholdCondition;
import mage.abilities.costs.common.DiscardSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.ContinuousRuleModifyingEffectImpl;
import mage.abilities.effects.common.continuous.BoostTargetEffect;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.AbilityWord;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.Outcome;

/**
 *
 * @author muz
 */
public final class ProftSinisterMastermind extends CardImpl {

    public ProftSinisterMastermind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{B}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.ROGUE);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);

        // Threshold -- You can't cast this spell unless there are seven or more cards in your graveyard.
        this.addAbility(new SimpleStaticAbility(Zone.ALL, new ProftSinisterMastermindEffect()).setAbilityWord(AbilityWord.THRESHOLD));

        // Menace
        this.addAbility(new MenaceAbility());

        // {B}, Discard this card: Target creature gets -3/-1 until end of turn.
        Ability ability = new SimpleActivatedAbility(
            Zone.HAND, new BoostTargetEffect(-3, -1), new ManaCostsImpl<>("{B}")
        );
        ability.addCost(new DiscardSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private ProftSinisterMastermind(final ProftSinisterMastermind card) {
        super(card);
    }

    @Override
    public ProftSinisterMastermind copy() {
        return new ProftSinisterMastermind(this);
    }
}

class ProftSinisterMastermindEffect extends ContinuousRuleModifyingEffectImpl {

    ProftSinisterMastermindEffect() {
        super(Duration.WhileOnBattlefield, Outcome.Detriment);
        staticText = "You can't cast this spell unless there are seven or more cards in your graveyard";
    }

    private ProftSinisterMastermindEffect(final ProftSinisterMastermindEffect effect) {
        super(effect);
    }

    @Override
    public ProftSinisterMastermindEffect copy() {
        return new ProftSinisterMastermindEffect(this);
    }

    @Override
    public boolean checksEventType(GameEvent event, Game game) {
        return event.getType() == GameEvent.EventType.CAST_SPELL;
    }

    @Override
    public boolean applies(GameEvent event, Ability source, Game game) {
        if (event.getSourceId().equals(source.getSourceId())) {
            return !ThresholdCondition.instance.apply(game, source);
        }
        return false;
    }
}
