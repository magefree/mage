package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersPreparedAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.OpponentDealtNoncombatDamageCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardSetInfo;
import mage.cards.PrepareCard;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetOpponent;
import mage.watchers.common.NoncombatDamageToPlayersWatcher;

import java.util.UUID;

/**
 * @author muz
 */
public final class WhiplashWordsmith extends PrepareCard {

    public WhiplashWordsmith(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B/R}", "Vicious Verse", CardType.SORCERY, "{B/R}");
        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.SORCERER);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // This creature enters prepared.
        this.addAbility(new EntersPreparedAbility());

        // As long as an opponent was dealt noncombat damage this turn, this creature has flying and haste.
        Ability ability = new SimpleStaticAbility(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(FlyingAbility.getInstance()),
            OpponentDealtNoncombatDamageCondition.THIS_TURN,
            "as long as an opponent was dealt noncombat damage this turn, this creature has flying"
        ));
        ability.addEffect(new ConditionalContinuousEffect(
            new GainAbilitySourceEffect(HasteAbility.getInstance()),
            OpponentDealtNoncombatDamageCondition.THIS_TURN,
            "and haste"
        ));
        this.addAbility(ability, new NoncombatDamageToPlayersWatcher());

        // Vicious Verse
        // Sorcery {B/R}
        // Vicious Verse deals 1 damage to target opponent.
        this.getSpellCard().getSpellAbility().addEffect(new DamageTargetEffect(1));
        this.getSpellCard().getSpellAbility().addTarget(new TargetOpponent());
    }

    private WhiplashWordsmith(final WhiplashWordsmith card) {
        super(card);
    }

    @Override
    public WhiplashWordsmith copy() {
        return new WhiplashWordsmith(this);
    }
}
