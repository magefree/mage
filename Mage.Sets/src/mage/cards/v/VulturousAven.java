package mage.cards.v;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ExploitCreatureTriggeredAbility;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.abilities.keyword.ExploitAbility;
import mage.abilities.keyword.FlyingAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author LevelX2
 */
public final class VulturousAven extends CardImpl {

    public VulturousAven(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{B}");
        this.subtype.add(SubType.BIRD);
        this.subtype.add(SubType.SHAMAN);
        this.power = new MageInt(2);
        this.toughness = new MageInt(3);

        // Flying
        this.addAbility(FlyingAbility.getInstance());

        // Exploit
        this.addAbility(new ExploitAbility());

        // When Vulturous Aven exploits a creature, you draw two cards and you lose 2 life.
        Ability ability = new ExploitCreatureTriggeredAbility(new DrawCardSourceControllerEffect(2, true), false);
        ability.addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
        this.addAbility(ability);

    }

    private VulturousAven(final VulturousAven card) {
        super(card);
    }

    @Override
    public VulturousAven copy() {
        return new VulturousAven(this);
    }
}
