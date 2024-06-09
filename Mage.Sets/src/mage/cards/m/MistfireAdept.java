package mage.cards.m;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SpellCastControllerTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.FlyingAbility;
import mage.abilities.keyword.ProwessAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.filter.StaticFilters;
import mage.target.common.TargetCreaturePermanent;

/**
 *
 * @author emerald000
 */
public final class MistfireAdept extends CardImpl {

    public MistfireAdept(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MONK);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // Prowess
        this.addAbility(new ProwessAbility());
        
        // Whenever you cast a noncreature spell, target creature gains flying until end of turn.
        Ability ability = new SpellCastControllerTriggeredAbility(new GainAbilityTargetEffect(FlyingAbility.getInstance(), Duration.EndOfTurn),
                StaticFilters.FILTER_SPELL_A_NON_CREATURE, false);
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private MistfireAdept(final MistfireAdept card) {
        super(card);
    }

    @Override
    public MistfireAdept copy() {
        return new MistfireAdept(this);
    }
}
