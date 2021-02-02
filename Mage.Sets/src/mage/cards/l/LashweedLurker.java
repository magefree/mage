
package mage.cards.l;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.costs.mana.ManaCostsImpl;
import mage.abilities.effects.common.CastSourceTriggeredAbility;
import mage.abilities.effects.common.PutOnLibraryTargetEffect;
import mage.abilities.keyword.EmergeAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterNonlandPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author fireshoes
 */
public final class LashweedLurker extends CardImpl {

    public LashweedLurker(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId,setInfo,new CardType[]{CardType.CREATURE},"{8}");
        this.subtype.add(SubType.ELDRAZI);
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(5);
        this.toughness = new MageInt(4);

        // Emerge {5}{G}{U}
        this.addAbility(new EmergeAbility(this, new ManaCostsImpl<>("{5}{G}{U}")));

        // When you cast Lashweed Lurker, you may put target nonland permanent on top of its owner's library.
        Ability ability = new CastSourceTriggeredAbility(new PutOnLibraryTargetEffect(true), true);
        ability.addTarget(new TargetPermanent(new FilterNonlandPermanent()));
        this.addAbility(ability);
    }

    private LashweedLurker(final LashweedLurker card) {
        super(card);
    }

    @Override
    public LashweedLurker copy() {
        return new LashweedLurker(this);
    }
}
