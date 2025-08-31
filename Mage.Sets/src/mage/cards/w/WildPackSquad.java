package mage.cards.w;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.triggers.BeginningOfCombatTriggeredAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class WildPackSquad extends CardImpl {

    public WildPackSquad(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{W}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // At the beginning of combat on your turn, up to one target creature gains first strike and vigilance until end of turn.
        Ability ability = new BeginningOfCombatTriggeredAbility(new GainAbilityTargetEffect(FirstStrikeAbility.getInstance())
                .setText("up to one target creature gains first strike"));
        ability.addEffect(new GainAbilityTargetEffect(VigilanceAbility.getInstance())
                .setText("and vigilance until end of turn"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        this.addAbility(ability);
    }

    private WildPackSquad(final WildPackSquad card) {
        super(card);
    }

    @Override
    public WildPackSquad copy() {
        return new WildPackSquad(this);
    }
}
