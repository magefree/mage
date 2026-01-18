package mage.cards.m;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeSourceCost;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.DrawCardSourceControllerEffect;
import mage.abilities.effects.common.LoseLifeSourceControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author Loki
 */
public final class MoriokReplica extends CardImpl {

    public MoriokReplica(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{3}");
        this.subtype.add(SubType.WARRIOR);
        this.power = new MageInt(2);
        this.toughness = new MageInt(2);

        // {1}{B}, Sacrifice Moriok Replica: You draw two cards and you lose 2 life.
        Ability ability = new SimpleActivatedAbility(
                new DrawCardSourceControllerEffect(2, true), new ManaCostsImpl<>("{1}{B}")
        );
        ability.addCost(new SacrificeSourceCost());
        ability.addEffect(new LoseLifeSourceControllerEffect(2).concatBy("and"));
        this.addAbility(ability);
    }

    private MoriokReplica(final MoriokReplica card) {
        super(card);
    }

    @Override
    public MoriokReplica copy() {
        return new MoriokReplica(this);
    }
}
