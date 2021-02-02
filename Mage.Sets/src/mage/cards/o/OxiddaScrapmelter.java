
package mage.cards.o;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.Ability;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.common.DestroyTargetEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;
import mage.filter.common.FilterArtifactPermanent;
import mage.target.TargetPermanent;

/**
 *
 * @author Loki, North
 */
public final class OxiddaScrapmelter extends CardImpl {

    public OxiddaScrapmelter(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{3}{R}");
        this.subtype.add(SubType.BEAST);

        this.power = new MageInt(3);
        this.toughness = new MageInt(3);

        // When Oxidda Scrapmelter enters the battlefield, destroy target artifact.
        Ability ability = new EntersBattlefieldTriggeredAbility(new DestroyTargetEffect());
        ability.addTarget(new TargetPermanent(new FilterArtifactPermanent()));
        this.addAbility(ability);
    }

    private OxiddaScrapmelter(final OxiddaScrapmelter card) {
        super(card);
    }

    @Override
    public OxiddaScrapmelter copy() {
        return new OxiddaScrapmelter(this);
    }

}
