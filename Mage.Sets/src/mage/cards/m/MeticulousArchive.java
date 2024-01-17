package mage.cards.m;

import mage.abilities.common.EntersBattlefieldTappedAbility;
import mage.abilities.common.EntersBattlefieldTriggeredAbility;
import mage.abilities.effects.keyword.SurveilEffect;
import mage.abilities.mana.BlueManaAbility;
import mage.abilities.mana.WhiteManaAbility;
import mage.cards.CardImpl;
import mage.cards.CardSetInfo;
import mage.constants.CardType;
import mage.constants.SubType;

import java.util.UUID;

/**
 * @author TheElk801
 */
public final class MeticulousArchive extends CardImpl {

    public MeticulousArchive(UUID ownerId, CardSetInfo setInfo) {
        super(ownerId, setInfo, new CardType[]{CardType.LAND}, "");

        this.subtype.add(SubType.PLAINS);
        this.subtype.add(SubType.ISLAND);

        // ({T}: Add {W} or {U}.)
        this.addAbility(new WhiteManaAbility());
        this.addAbility(new BlueManaAbility());

        // Meticulous Archive enters the battlefield tapped.
        this.addAbility(new EntersBattlefieldTappedAbility());

        // When Meticulous Archive enters the battlefield, surveil 1.
        this.addAbility(new EntersBattlefieldTriggeredAbility(new SurveilEffect(1, false)));
    }

    private MeticulousArchive(final MeticulousArchive card) {
        super(card);
    }

    @Override
    public MeticulousArchive copy() {
        return new MeticulousArchive(this);
    }
}
