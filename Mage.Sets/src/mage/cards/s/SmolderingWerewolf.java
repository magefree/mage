
package mage.cards.s;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.Effect;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.effects.common.TransformSourceEffect;
import mage.abilities.keyword.TransformAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author fireshoes
 */
public final class SmolderingWerewolf extends CardImpl {

    public SmolderingWerewolf(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{2}{R}{R}");
        this.subtype.add(SubType.WEREWOLF);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        this.secondSideCardClazz = mage.cards.e.EruptingDreadwolf.class;

        // When Smoldering Werewolf enters the battlefield, it deals 1 damage to each of up to two target creatures.
        Effect effect = new DamageTargetEffect(1);
        effect.setText("it deals 1 damage to each of up to two target creatures");
        Ability ability = new EntersBattlefieldTriggeredAbility(effect, false);
        ability.addTarget(new TargetCreaturePermanent(0, 2));
        this.addAbility(ability);

        // {4}{R}{R}: Transform Smoldering Werewolf.
        this.addAbility(new TransformAbility());
        this.addAbility(new SimpleActivatedAbility(Zone.BATTLEFIELD, new TransformSourceEffect(), new ManaCostsImpl("{4}{R}{R}")));
    }

    private SmolderingWerewolf(final SmolderingWerewolf card) {
        super(card);
    }

    @Override
    public SmolderingWerewolf copy() {
        return new SmolderingWerewolf(this);
    }
}