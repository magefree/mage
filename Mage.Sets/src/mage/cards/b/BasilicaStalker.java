package mage.cards.b;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.DealsCombatDamageToAPlayerTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.GainLifeEffect;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.keyword.DisguiseAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class BasilicaStalker extends CardImpl {

    public BasilicaStalker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{B}");

        this.subtype.add(SubType.VAMPIRE);
        this.subtype.add(SubType.DETECTIVE);
        this.power = new MageInt(3);
        this.toughness = new MageInt(4);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Whenever Basilica Stalker deals combat damage to a player, you gain 1 life and surveil 1.
        Ability ability = new DealsCombatDamageToAPlayerTriggeredAbility(new GainLifeEffect(1), false);
        ability.addEffect(new SurveilEffect(1).concatBy("and"));
        this.addAbility(ability);

        // Disguise {4}{B}
        this.addAbility(new DisguiseAbility(this, new ManaCostsImpl<>("{4}{B}")));
    }

    private BasilicaStalker(final BasilicaStalker card) {
        super(card);
    }

    @Override
    public BasilicaStalker copy() {
        return new BasilicaStalker(this);
    }
}
