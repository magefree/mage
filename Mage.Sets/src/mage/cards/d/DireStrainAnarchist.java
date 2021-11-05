package mage.cards.d;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.DamageTargetEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.MenaceAbility;
import mage.abilities.keyword.NightboundAbility;
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
public final class DireStrainAnarchist extends CardImpl {

    public DireStrainAnarchist(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");

        this.subtype.add(SubType.WEREWOLF);
        this.power = new MageInt(5);
        this.toughness = new MageInt(5);
        this.color.setRed(true);
        this.nightCard = true;

        // Menace
        this.addAbility(new MenaceAbility());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Dire-Strain Anarchist attacks, it deals 2 damage to each of up to one target creature, up to one target player, and/or up to one target planeswalker.
        Ability ability = new AttacksTriggeredAbility(new DamageTargetEffect(2).setText("it deals 2 damage to each of up to one target creature, up to one target player, and/or up to one target planeswalker"));
        ability.addTarget(new TargetCreaturePermanent(0, 1));
        ability.addTarget(new TargetPlayer(0, 1, false));
        ability.addTarget(new TargetPlaneswalkerPermanent(0, 1));
        this.addAbility(ability);

        // Nightbound
        this.addAbility(new NightboundAbility());
    }

    private DireStrainAnarchist(final DireStrainAnarchist card) {
        super(card);
    }

    @Override
    public DireStrainAnarchist copy() {
        return new DireStrainAnarchist(this);
    }
}
