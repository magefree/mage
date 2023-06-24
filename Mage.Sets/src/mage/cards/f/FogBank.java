package mage.cards.f;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.PreventCombatDamageBySourceEffect;
import mage.abilities.effects.common.PreventCombatDamageToSourceEffect;
import mage.abilities.keyword.DefenderAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.Duration;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author jeffwadsworth
 */
public final class FogBank extends CardImpl {

    public FogBank(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{U}");
        this.subtype.add(SubType.WALL);

        this.power = new MageInt(0);
        this.toughness = new MageInt(2);

        // Defender
        this.addAbility(DefenderAbility.getInstance());

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Prevent all combat damage that would be dealt to and dealt by Fog Bank.
        Ability ability = new SimpleStaticAbility(new PreventCombatDamageToSourceEffect(Duration.WhileOnBattlefield)
                .setText("prevent all combat damage that would be dealt to"));
        ability.addEffect(new PreventCombatDamageBySourceEffect(Duration.WhileOnBattlefield)
                .setText("and dealt by {this}"));
        this.addAbility(ability);
    }

    private FogBank(final FogBank card) {
        super(card);
    }

    @Override
    public FogBank copy() {
        return new FogBank(this);
    }
}
