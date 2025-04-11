package mage.cards.h;

import java.util.UUID;
import mage.MageInt;
import mage.constants.SubType;
import mage.constants.SuperType;
import mage.abilities.keyword.VigilanceAbility;
import mage.abilities.common.SimpleStaticAbility;
import mage.abilities.effects.common.replacement.GraveyardFromAnywhereExileReplacementEffect;
import mage.abilities.effects.common.ruleModifying.PlayFromGraveyardControllerEffect;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;

/**
 * @author balazskristof
 */
public final class HadesSorcererOfEld extends CardImpl {

    public HadesSorcererOfEld(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.CREATURE}, "");
        
        this.supertype.add(SuperType.LEGENDARY);
        this.subtype.add(SubType.AVATAR);
        this.power = new MageInt(6);
        this.toughness = new MageInt(6);

        this.color.setBlue(true);
        this.color.setBlack(true);
        this.nightCard = true;

        // Vigilance
        this.addAbility(VigilanceAbility.getInstance());

        // Echo of the Lost -- During your turn you may play cards from your graveyard.
        this.addAbility(new SimpleStaticAbility(PlayFromGraveyardControllerEffect.playCards()).withFlavorWord("Echo of the Lost"));

        // If a card or token would be put into your graveyard from anywhere, exile it instead.
        this.addAbility(new SimpleStaticAbility(new GraveyardFromAnywhereExileReplacementEffect(true, true)));
    }

    private HadesSorcererOfEld(final HadesSorcererOfEld card) {
        super(card);
    }

    @Override
    public HadesSorcererOfEld copy() {
        return new HadesSorcererOfEld(this);
    }
}
