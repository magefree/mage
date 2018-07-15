package mage.cards.t;

import java.util.UUID;
import mage.MageInt;
import mage.abilities.effects.common.InfoEffect;
import mage.constants.SubType;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 *
 * @author noahg
 */
public final class TimmerianFiends extends CardImpl {

    public TimmerianFiends(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "{1}{B}{B}");
        
        this.subtype.add(SubType.HORROR);
        this.power = new MageInt(1);
        this.toughness = new MageInt(1);

        // Remove Timmerian Fiends from your deck before playing if you're not playing for ante.
        // {B}{B}{B}, Sacrifice Timmerian Fiends: The owner of target artifact may ante the top card of his or her library. If that player doesn't, exchange ownership of that artifact and Timmerian Fiends. Put the artifact card into your graveyard and Timmerian Fiends from anywhere into that player's graveyard. This change in ownership is permanent.
        this.getSpellAbility().addEffect(new InfoEffect("Remove Timmerian Fiends from your deck before playing " +
                "if you're not playing for ante.\n{B}{B}{B}, Sacrifice Timmerian Fiends: The owner of target artifact" +
                " may ante the top card of his or her library. If that player doesn't, exchange ownership of that " +
                "artifact and Timmerian Fiends. Put the artifact card into your graveyard and Timmerian Fiends from " +
                "anywhere into that player's graveyard. This change in ownership is permanent."));
    }

    public TimmerianFiends(final TimmerianFiends card) {
        super(card);
    }

    @Override
    public TimmerianFiends copy() {
        return new TimmerianFiends(this);
    }
}
