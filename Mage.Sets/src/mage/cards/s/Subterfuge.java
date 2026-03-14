package mage.cards.s;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.dynamicvalue.common.SavedDamageValue;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.EncoreAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class Subterfuge extends CardImpl {

    public Subterfuge(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{4}{U}");

        this.subtype.add(SubType.ELEMENTAL);
        this.subtype.add(SubType.INCARNATION);
        this.power = new MageInt(3);
        this.toughness = new MageInt(5);

        // When this creature enters, target creature gains flying and "Whenever this creature deals combat damage to a player, draw that many cards" until end of turn.
        Ability ability = new EntersBattlefieldTriggeredAbility(
                new GainAbilityTargetEffect(FlyingAbility.getInstance())
                        .setText("target creature gains flying")
        );
        ability.addEffect(new GainAbilityTargetEffect(
                new DealsCombatDamageToAPlayerTriggeredAbility(new DrawCardSourceControllerEffect(SavedDamageValue.MANY))
        ).setText("and \"Whenever this creature deals combat damage to a player, draw that many cards\" until end of turn."));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // Encore {7}{U}{U}
        this.addAbility(new EncoreAbility(new ManaCostsImpl<>("{7}{U}{U}")));
    }

    private Subterfuge(final Subterfuge card) {
        super(card);
    }

    @Override
    public Subterfuge copy() {
        return new Subterfuge(this);
    }
}
