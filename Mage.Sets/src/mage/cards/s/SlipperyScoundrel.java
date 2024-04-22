package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.condition.common.CitysBlessingCondition;
import mage.abilities.decorator.ConditionalContinuousEffect;
import mage.abilities.decorator.ConditionalRestrictionEffect;
import mage.abilities.effects.ContinuousEffect;
import mage.abilities.effects.Effect;
import mage.abilities.effects.RestrictionEffect;
import mage.abilities.effects.common.combat.CantBeBlockedSourceEffect;
import mage.abilities.effects.common.continuous.GainAbilitySourceEffect;
import mage.abilities.hint.common.CitysBlessingHint;
import mage.abilities.keyword.AscendAbility;
import mage.abilities.keyword.HexproofAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.Zone;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class SlipperyScoundrel extends CardImpl {

    public SlipperyScoundrel(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{U}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.PIRATE);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Ascend
        this.addAbility(new AscendAbility());

        // As long as you have the city's blessing Slippery Scoundrel has Hexproof and can't be blocked.
        ContinuousEffect boostSource = new GainAbilitySourceEffect(HexproofAbility.getInstance(), Duration.WhileOnBattlefield);
        ConditionalContinuousEffect effect = new ConditionalContinuousEffect(boostSource, CitysBlessingCondition.instance,
                "As long as you have the city's blessing, {this} has hexproof");
        Ability ability = new SimpleStaticAbility(Zone.BATTLEFIELD, effect);
        RestrictionEffect restrictionEffect = new CantBeBlockedSourceEffect(Duration.WhileOnBattlefield);
        Effect effect2 = new ConditionalRestrictionEffect(restrictionEffect, CitysBlessingCondition.instance)
                .setText("and can't be blocked");
        ability.addEffect(effect2);
        ability.addHint(CitysBlessingHint.instance);
        this.addAbility(ability);
    }

    private SlipperyScoundrel(final SlipperyScoundrel card) {
        super(card);
    }

    @Override
    public SlipperyScoundrel copy() {
        return new SlipperyScoundrel(this);
    }
}
