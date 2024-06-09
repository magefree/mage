
package mage.cards.e;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.LandfallAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.continuous.BecomesCreatureTargetEffect;
import mage.abilities.effects.common.continuous.GainAbilityControlledEffect;
import mage.abilities.keyword.HasteAbility;
import mage.abilities.keyword.TrampleAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.constants.Duration;
import mage.constants.Zone;
import mage.filter.FilterPermanent;
import mage.filter.common.FilterControlledLandPermanent;
import mage.game.permanent.token.TokenImpl;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class EmbodimentOfFury extends CardImpl {
    
    private static final FilterPermanent filterLandCreatures = new FilterPermanent("Land creatures");

    static {
        filterLandCreatures.add(CardType.LAND.getPredicate());
        filterLandCreatures.add(CardType.CREATURE.getPredicate());
    }

    public EmbodimentOfFury(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{3}{R}");
        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(4);
        this.toughness = new MageInt(3);

        // Trample
        this.addAbility(TrampleAbility.getInstance());
        
        // Land creatures you control have trample.
        this.addAbility(new SimpleStaticAbility(Zone.BATTLEFIELD, new GainAbilityControlledEffect(TrampleAbility.getInstance(), Duration.WhileOnBattlefield, filterLandCreatures)));
        
        // <i>Landfall</i> &mdash; Whenever a land enters the battlefield under your control, you may have target land you control 
        // become a 3/3 Elemental creature with haste until end of turn. It's still a land.
        Ability ability = new LandfallAbility(new BecomesCreatureTargetEffect(new EmbodimentOfFuryToken(), false, true, Duration.EndOfTurn), true);
        ability.addTarget(new TargetPermanent(new FilterControlledLandPermanent()));
        this.addAbility(ability);
    }

    private EmbodimentOfFury(final EmbodimentOfFury card) {
        super(card);
    }

    @Override
    public EmbodimentOfFury copy() {
        return new EmbodimentOfFury(this);
    }
}

class EmbodimentOfFuryToken extends TokenImpl {

    public EmbodimentOfFuryToken() {
        super("", "3/3 Elemental creature with haste");
        this.cardType.add(CardType.CREATURE);

        this.subtype.add(SubType.ELEMENTAL);
        this.power = new MageInt(3);
        this.toughness = new MageInt(3);
        this.addAbility(HasteAbility.getInstance());
    }
    private EmbodimentOfFuryToken(final EmbodimentOfFuryToken token) {
        super(token);
    }

    public EmbodimentOfFuryToken copy() {
        return new EmbodimentOfFuryToken(this);
    }
}
