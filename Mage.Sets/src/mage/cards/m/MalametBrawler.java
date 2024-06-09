package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.effects.common.continuous.GainAbilityTargetEffect;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetAttackingCreature;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MalametBrawler extends CardImpl {

    public MalametBrawler(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{G}");

        this.subtype.add(SubType.CAT);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Malamet Brawler attacks, target attacking creature gains trample until end of turn.
        Ability ability = new AttacksTriggeredAbility(new GainAbilityTargetEffect(TrampleAbility.getInstance()));
        ability.addTarget(new TargetAttackingCreature());
        this.addAbility(ability);
    }

    private MalametBrawler(final MalametBrawler card) {
        super(card);
    }

    @Override
    public MalametBrawler copy() {
        return new MalametBrawler(this);
    }
}
