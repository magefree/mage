package mage.cards.k;

import java.util.UUID;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.ActivateAsSorceryActivatedAbility;
import mage.abilities.common.AttacksTriggeredAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.continuous.GainControlTargetEffect;
import mage.constants.Duration;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.FirstStrikeAbility;
import mage.abilities.keyword.HasteAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.TreasureToken;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author Cguy7777
 */
public final class KelloggDangerousMind extends CardImpl {

    private static final FilterControlledPermanent filterTreasures = new FilterControlledPermanent(SubType.TREASURE, "Treasures");

    public KelloggDangerousMind(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{R}");

        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.MERCENARY);
        this.power = new MageInt(3);
        this.toughness = new MageInt(2);

        // First strike
        this.addAbility(FirstStrikeAbility.getInstance());

        // Haste
        this.addAbility(HasteAbility.getInstance());

        // Whenever Kellogg, Dangerous Mind attacks, create a Treasure token.
        this.addAbility(new AttacksTriggeredAbility(new CreateTokenEffect(new TreasureToken())));

        // Sacrifice five Treasures: Gain control of target creature for as long as you control Kellogg. Activate only as a sorcery.
        Ability ability = new ActivateAsSorceryActivatedAbility(
                new GainControlTargetEffect(Duration.WhileControlled),
                new SacrificeTargetCost(5, filterTreasures));
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);
    }

    private KelloggDangerousMind(final KelloggDangerousMind card) {
        super(card);
    }

    @Override
    public KelloggDangerousMind copy() {
        return new KelloggDangerousMind(this);
    }
}
