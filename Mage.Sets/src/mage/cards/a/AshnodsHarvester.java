package mage.cards.a;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.ExileTargetEffect;
import mage.abilities.keyword.UnearthAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.target.common.TargetCardInGraveyard;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class AshnodsHarvester extends CardImpl {

    public AshnodsHarvester(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.ARTIFACT, CardType.CREATURE}, "{2}");

        this.subtype.add(SubType.CONSTRUCT);
        this.power = new MageInt(3);
        this.toughness = new MageInt(1);

        // Whenever Ashnod's Harvester attacks, exile target card from a graveyard.
        Ability ability = new AttacksTriggeredAbility(new ExileTargetEffect());
        ability.addTarget(new TargetCardInGraveyard());
        this.addAbility(ability);

        // Unearth {1}{B}
        this.addAbility(new UnearthAbility(new ManaCostsImpl<>("{1}{B}")));
    }

    private AshnodsHarvester(final AshnodsHarvester card) {
        super(card);
    }

    @Override
    public AshnodsHarvester copy() {
        return new AshnodsHarvester(this);
    }
}
