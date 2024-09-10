package mage.cards.c;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.TapSourceCost;
import mage.abilities.effects.common.combat.AttacksIfAbleTargetEffect;
import mage.abilities.effects.common.combat.BlocksIfAbleTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.target.common.TargetCreaturePermanent;

/**
 * @author magenoxx_at_gmail.com
 */
public final class CourtlyProvocateur extends CardImpl {

    public CourtlyProvocateur(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{2}{U}");
        this.subtype.add(SubType.HUMAN);
        this.subtype.add(SubType.WIZARD);

        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // {tap}: Target creature attacks this turn if able.
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new AttacksIfAbleTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        ability.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability);

        // {tap}: Target creature blocks this turn if able.
        Ability ability2 = new SimpleActivatedAbility(Zone.BATTLEFIELD, new BlocksIfAbleTargetEffect(Duration.EndOfTurn), new TapSourceCost());
        ability2.addTarget(new TargetCreaturePermanent());
        this.addAbility(ability2);
    }

    private CourtlyProvocateur(final CourtlyProvocateur card) {
        super(card);
    }

    @Override
    public CourtlyProvocateur copy() {
        return new CourtlyProvocateur(this);
    }
}
