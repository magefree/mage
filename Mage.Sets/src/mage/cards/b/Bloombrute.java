package mage.cards.b;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.GainLifeControllerTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.LifelinkAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.constants.SubType;
import mage.target.common.TargetCreaturePermanent;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author muz
 */
public final class Bloombrute extends CardImpl {

    public Bloombrute(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{G}{W}");

        this.subtype.add(SubType.PLANT);
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);

        // Whenever you gain life, draw a card. This ability triggers only once each turn.
        this.addAbility(new GainLifeControllerTriggeredAbility(new DrawCardSourceControllerEffect(1))
            .setTriggersLimitEachTurn(1));

        // {4}{G}{W}: Target creature gains trample and lifelink until end of turn.
        Ability ability = new SimpleActivatedAbility(
            new GainAbilityTargetEffect(TrampleAbility.getInstance()).setText("target creature gains trample"),
            new ManaCostsImpl<>("{4}{G}{W}")
        );
        ability.addEffect(new GainAbilityTargetEffect(LifelinkAbility.getInstance()).setText("and lifelink until end of turn"));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private Bloombrute(final Bloombrute card) {
        super(card);
    }

    @Override
    public Bloombrute copy() {
        return new Bloombrute(this);
    }
}
