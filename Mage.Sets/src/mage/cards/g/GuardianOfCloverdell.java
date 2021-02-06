package mage.cards.g;

import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.common.SimpleActivatedAbility;
import mage.abilities.costs.common.SacrificeTargetCost;
import mage.abilities.costs.mana.ColoredManaCost;
import mage.abilities.effects.common.CreateTokenEffect;
import mage.abilities.effects.common.GainLifeEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.ColoredManaSymbol;
import mage.constants.SubType;
import mage.constants.Zone;
import mage.filter.common.FilterControlledPermanent;
import mage.game.permanent.token.KithkinSoldierToken;
import mage.target.common.TargetControlledPermanent;

import java.util.UUID;

/**
 * @author Loki
 */
public final class GuardianOfCloverdell extends CardImpl {

    private static final FilterControlledPermanent filter = new FilterControlledPermanent("Kithkin");

    static {
        filter.add(SubType.KITHKIN.getPredicate());
    }

    public GuardianOfCloverdell(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{5}{G}{G}");
        this.subtype.add(SubType.TREEFOLK);
        this.subtype.add(SubType.SHAMAN);

        this.power = new MageInt(4);
        this.toughness = new MageInt(5);
        this.addAbility(new EntersBattlefieldTriggeredAbility(new CreateTokenEffect(new KithkinSoldierToken(), 3), false));
        Ability ability = new SimpleActivatedAbility(Zone.BATTLEFIELD, new GainLifeEffect(1), new ColoredManaCost(ColoredManaSymbol.G));
        ability.addCost(new SacrificeTargetCost(new TargetControlledPermanent(filter)));
        this.addAbility(ability);
    }

    private GuardianOfCloverdell(final GuardianOfCloverdell card) {
        super(card);
    }

    @Override
    public GuardianOfCloverdell copy() {
        return new GuardianOfCloverdell(this);
    }
}
