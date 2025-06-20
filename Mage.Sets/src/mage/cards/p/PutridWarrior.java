package mage.cards.p;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.Mode;
import mage.abilities.common.DealsDamageSourceTriggeredAbility;
import mage.abilities.effects.common.GainLifeAllEffect;
import mage.abilities.effects.common.LoseLifeAllPlayersEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LoneFox
 */
public final class PutridWarrior extends CardImpl {

    public PutridWarrior(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{W}{B}");
        this.subtype.add(SubType.ZOMBIE);
        this.subtype.add(SubType.SOLDIER);
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // Whenever Putrid Warrior deals damage, choose one - Each player loses 1 life; or each player gains 1 life.
        Ability ability = new DealsDamageSourceTriggeredAbility(new LoseLifeAllPlayersEffect(1));
        ability.addMode(new Mode(new GainLifeAllEffect(1)));
        this.addAbility(ability);
    }

    private PutridWarrior(final PutridWarrior card) {
        super(card);
    }

    @Override
    public PutridWarrior copy() {
        return new PutridWarrior(this);
    }
}
