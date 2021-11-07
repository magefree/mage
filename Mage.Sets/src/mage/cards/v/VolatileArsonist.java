package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.DayboundAbility;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.TargetPlayer;
import mage.target.common.TargetCreaturePermanent;
import mage.target.common.TargetPlaneswalkerPermanent;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class VolatileArsonist extends CardImpl {

    public VolatileArsonist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}{R}");

        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(4);
        this.toughness = new MageInt(4);
        this.secondSideCardClazz = mage.cards.d.DireStrainAnarchist.class;

        // Menace
        this.addAbility(new MenaceAbility());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Volatile Arsonist attacks, it deals 1 damage to each of up to one target creature, up to one target player, and/or up to one target planeswalker.
        Ability ability = new AttacksTriggeredAbility(new DamageTargetEffect(1)
                .setText("it deals 1 damage to each of up to one target creature, up to one target player, and/or up to one target planeswalker"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.addTarget(new TargetPlayer(0, 1, false));
        ability.addTarget(new TargetPlaneswalkerPermanent(0, 1));
        this.addAbility(ability);

        // Daybound
        this.addAbility(new DayboundAbility());
    }

    private VolatileArsonist(final VolatileArsonist card) {
        super(card);
    }

    @Override
    public VolatileArsonist copy() {
        return new VolatileArsonist(this);
    }
}
